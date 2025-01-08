package osaki.mylang.repository;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.exceptions.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo {
    // PrgState current_program;
    String log_file_path;
    List<PrgState> program_state_list = new ArrayList<>();

    public Repo(PrgState current_program, String log_file_path) {
        this.log_file_path = log_file_path;
        // this.current_program = current_program;
        program_state_list.add(current_program);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.log_file_path, true)));
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

        /* logFile.println("THREAD WITH ID " + state.get);
        logFile.println("ExeStack:");
        logFile.println(state.getStk().myString());
        logFile.println("SymTable:");
        logFile.println(state.getSymTable().myString());
        logFile.println("Out:");
        logFile.println(state.getOut().myString());
        logFile.println("FileTable:");
        logFile.println(state.getFileTable().myKeyString());
        logFile.println("Heap:");
        logFile.println(state.getHeap().myString()); */
        logFile.print(state.toString());

        logFile.flush();
        logFile.close();
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.program_state_list;
    }

    @Override
    public void setPrgList(List<PrgState> new_state_list) {
        this.program_state_list = new_state_list;
    }
}
