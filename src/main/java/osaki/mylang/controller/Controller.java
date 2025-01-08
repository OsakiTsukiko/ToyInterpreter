package osaki.mylang.controller;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.stmt.IStmt;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.StringValue;
import osaki.mylang.repository.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static osaki.mylang.controller.GarbageCollector.*;

public class Controller {
    public IRepo repo;
    public ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        try {
            prgList.forEach(prg -> {
                try {
                    repo.logPrgStateExec(prg);
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RuntimeException e) {
            // Unwrap and rethrow the original MyException
            if (e.getCause() instanceof MyException) {
                throw (MyException) e.getCause();
            }
            throw e; // Rethrow if it's an unexpected exception
        }
        // DONT WE ALL LOVE FUNCTIONAL PROGRAMMING

        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep)).toList();


        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)

        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            // System.out.println("EXCEPTIONN: " + e.getMessage());
                            throw new RuntimeException(e);
                        }
                        // return null;
                    }).filter(Objects::nonNull).toList();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(!prgList.isEmpty()) {
            conservativeGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrgList()); // HUH????
        }
        executor.shutdownNow();
        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository
        // update the repository state
        repo.setPrgList(prgList);
    }

    public void typeCheck() throws MyException {
        this.repo.getPrgList().get(0).typeCheck();
    }




    public int getPrgCount() {
        return repo.getPrgList().size();
    }

    public Map<Integer, IValue> getHeap() {
        return repo.getPrgList().get(0).getHeap().getContent();
    }
    public Map<String, IValue> getSymTable(Integer id) {
        for (PrgState prg : repo.getPrgList()) {
            if (Objects.equals(prg.id, id)) return prg.getSymTable().getContent();
        }
        return null; // should be unreachabe
    }

    public Iterator<IStmt> getExeStack(Integer id) {
        for (PrgState prg : repo.getPrgList()) {
            if (Objects.equals(prg.id, id)) return prg.getStk().getContent().iterator();
        }
        return null; // should be unreachabe
    }

    public Iterator<IValue> getOut() {
        return repo.getPrgList().get(0).getOut().iterator();
    }

    public Iterator<StringValue> getFileTable() {
        return repo.getPrgList().get(0).getFileTable().getContent().keySet().iterator();
    }

    public Iterator<PrgState> getProgStates() {
        return repo.getPrgList().iterator();
    }
}