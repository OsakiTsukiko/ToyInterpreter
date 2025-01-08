package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IValue;

public class WhileStmt implements IStmt {
    IExp exp;
    IStmt stmt;

    public WhileStmt(IExp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue condition = exp.eval(state.getSymTable(), state.getHeap());

        if (!(condition instanceof BoolValue boolCondition)) {
            throw new MyException("Conditional expression is not a boolean.");
        }

        if (boolCondition.getVal()) {
            state.getStk().push(deepCopy());
            state.getStk().push(stmt);
        }

        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("The condition of WHILE has not the type bool");
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "while(" + exp.toString() + ") {" + stmt.toString() + "}";
    }
}
