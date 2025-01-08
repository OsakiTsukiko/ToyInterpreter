package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyStack;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyStack<IStmt> stk = state.getStk();
        IMyDictionary<String, IValue> symTbl= state.getSymTable();

        if (symTbl.has(id)) {
            IValue val = exp.eval(symTbl, state.getHeap());
            IType typeID = (symTbl.get(id)).getType();
            if (val.getType().equals(typeID)) {
                symTbl.set(id, val);
            } else throw new MyException("declared type of variable" + id + " and type of the assigned expression do not match!!1");
        } else throw new MyException("the used variable" + id + " was not declared before");

        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.get(id);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp)) return typeEnv;
        else throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
