package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.IntType;
import osaki.mylang.model.types.StringType;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.IntValue;
import osaki.mylang.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    IExp exp;
    String id;

    public ReadFileStmt(IExp exp, String id) {
        this.exp = exp;
        this.id = id;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyDictionary<String, IValue> symTable = state.getSymTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symTable.has(this.id)) throw new MyException("Undefined variable " + id + " !!1");

        IValue idv = symTable.get(this.id);
        if (!idv.getType().equals(new IntType())) throw new MyException("Variable is not of type Int!!1");

        IValue ev = exp.eval(symTable, state.getHeap());
        if (!ev.getType().equals(new StringType())) throw new MyException("Expresion NOT of type String!!1");

        StringValue sv = (StringValue) ev;
        if (fileTable.has(sv)) {
            try {
                BufferedReader bufferedReader = fileTable.get(sv);
                String line = bufferedReader.readLine();
                IntValue iv;
                if (line == null) {
                    iv = new IntValue(0);
                } else {
                    iv = new IntValue(Integer.parseInt(line));
                }
                symTable.set(this.id, iv);
            } catch (IOException exception) {
                throw new MyException("Unable to READ file!!1");
            }
        } else {
            throw new MyException("File is not OPENED!!1");
        }
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.get(id);
        if (!varType.equals(new IntType()))
            throw new MyException(this.id + " is not of int type!");
        IType expressionType = this.exp.typecheck(typeEnv);
        if (!expressionType.equals(new StringType()))
            throw new MyException(this.exp + " does not result in a value of string type!");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), id);
    }

    @Override
    public String toString() {
        return "read(" + this.exp.toString() + " -> " + this.id + ")";
    }
}
