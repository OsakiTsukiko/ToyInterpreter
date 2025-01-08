package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyStack;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyStack<IStmt> stack = state.getStk();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(this.first.deepCopy(), this.second.deepCopy());
    }

    @Override
    public String toString() {
        return "( " + first.toString() + " ; " + second.toString() + " )";
    }
}
