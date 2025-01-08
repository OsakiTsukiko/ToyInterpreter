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
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {
    IExp exp;

    public OpenRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyDictionary<String, IValue> symTable = state.getSymTable();
        IMyDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IValue v = exp.eval(symTable, state.getHeap());

        if (v.getType().equals(new StringType())) {
            StringValue s = (StringValue) v;
            if (fileTable.has(s)) throw new MyException("File already opened!!1");
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(s.getVal()));
                fileTable.set(s, bufferedReader);
            } catch (IOException exception) {
                throw new MyException(exception.getMessage());
            }
            return null;
        } else {
            throw new MyException("Expresion does not evaluate to a StringValue!!1");
        }
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        }
        throw new MyException("Open R File, expr must be a string!");
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "open(" + this.exp.toString() + ")";
    }
}
