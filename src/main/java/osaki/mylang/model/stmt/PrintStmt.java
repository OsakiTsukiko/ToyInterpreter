package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        state.getOut().add(val);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }

    @Override
    public String toString(){
        return "print(" + exp.toString() + ")";
    };
}
