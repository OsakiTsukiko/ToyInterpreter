package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyStack;
import osaki.mylang.model.adts.MyStack;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public class ForkStmt implements IStmt {
    private final IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyStack<IStmt> newExeStack = new MyStack<>();
        newExeStack.push(this.stmt);
        IMyDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy();
        PrgState.get_new_id();

        return new PrgState(
                newExeStack,
                newSymTable,
                state.getOut(),
                state.getFileTable(),
                state.getHeap()
        );
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(this.stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "fork( " + this.stmt.toString() + " )";
    }
}
