package osaki.mylang.model;

import osaki.mylang.model.adts.*;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.stmt.IStmt;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.StringValue;

import java.io.BufferedReader;

public class PrgState {
    IMyStack<IStmt> exeStack;
    IMyDictionary<String, IValue> symTable;
    IMyList<IValue> out;
    IStmt originalProgram;
    IMyDictionary<StringValue, BufferedReader> fileTable;
    IMyHeap<Integer, IValue> heap;

    public final Integer id;
    static Integer lastUsableID = 1;

    public static Integer get_new_id() {
        return lastUsableID++;
    }

    public PrgState (
            IMyStack<IStmt> stk,
            IMyDictionary<String, IValue> symTable,
            IMyList<IValue> out,
            IMyDictionary<StringValue, BufferedReader> fileTable,
            IMyHeap<Integer, IValue> heap,
            IStmt prg
    ) {
        this.exeStack = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = prg.deepCopy();
        this.exeStack.push(prg);
        this.id = lastUsableID;
    }

    public PrgState (
            IMyStack<IStmt> stk,
            IMyDictionary<String, IValue> symTable,
            IMyList<IValue> out,
            IMyDictionary<StringValue, BufferedReader> fileTable,
            IMyHeap<Integer, IValue> heap
    ) {
        this.exeStack = stk;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = lastUsableID;
    }

    public IMyStack<IStmt> getStk() {
        return this.exeStack;
    }

    public IMyDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IMyList<IValue> getOut() {
        return out;
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IMyHeap<Integer, IValue> getHeap() {
        return this.heap;
    }

    public String printExeStack() {
        return exeStack.toString();
    }

    public String printSymTable() {
        return symTable.toString();
    }

    public String getOutput() {
        StringBuilder res = new StringBuilder();
        for (IValue v : out) {
            res.append(v.toString());
        }
        return res.toString();
    }

    public Boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty()) throw new MyException("PrgState stack is empty!!1");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public void typeCheck() throws MyException{
        originalProgram.typecheck(new MyDictionary<>());
    }

    public String toString() {
        return "----- THREAD WITH ID " + id.toString() + " -----\n" +
                "ExeStack:\n" +
                exeStack.myString() + "\n" +
                "SymTable:\n" +
                symTable.myString() + "\n" +
                "Out:\n" +
                out.myString() + "\n" +
                "FileTable:\n" +
                fileTable.myKeyString() + "\n" +
                "Heap:\n" +
                heap.myString() + "\n";
    }
}
