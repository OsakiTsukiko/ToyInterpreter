package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IValue;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenStmt;
    IStmt elseStmt;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        this.exp = e;
        this.thenStmt = t;
        this.elseStmt = el;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condition = exp.eval(state.getSymTable(), state.getHeap());

        if (!(condition instanceof BoolValue boolCondition)) {
            throw new MyException("Conditional expression is not a boolean.");
        }

        if (boolCondition.getVal()) {
            state.getStk().push(thenStmt);
        } else {
            state.getStk().push(elseStmt);
        }

        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStmt.typecheck(typeEnv.deepCopy());
            elseStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }

    @Override
    public String toString() {
        return "IF ( " + exp.toString() + " ) THEN ( " + thenStmt.toString() + " ) ELSE ( " + elseStmt.toString() + " )";
    }
}
