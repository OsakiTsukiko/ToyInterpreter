package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.StringType;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyDictionary<String, IValue> symTable = state.getSymTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue v = exp.eval(symTable, state.getHeap());

        if (!v.getType().equals(new StringType())) throw new MyException("Expresion is not of type STRING!!1");

        StringValue sv = (StringValue) v;
        if (!fileTable.has(sv)) throw new MyException("File not OPENED!!1");

        try {
            BufferedReader bufferedReader = fileTable.get(sv);
            bufferedReader.close();
            fileTable.remove(sv);
        } catch (IOException exception) {
            throw new MyException(exception.getMessage());
        }

        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType expressionType = this.exp.typecheck(typeEnv);
        if (!expressionType.equals(new StringType()))
            throw new MyException(this.exp + " does not result in a value of string type!");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "close(" + this.exp.toString() + ")";
    }
}
