package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.RefType;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.RefValue;

public class heapWriteStmt implements IStmt {
    String var_name;
    IExp value;

    public heapWriteStmt(String var_name, IExp value) {
        this.var_name = var_name;
        this.value = value;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().has(var_name)) throw new MyException("Undefined variable! " + var_name);
        IValue v = state.getSymTable().get(var_name);
        if (!(v.getType() instanceof RefType)) throw new MyException("Variable to of type RefType! " + v.getType().toString());
        Integer address = ((RefValue)v).getAddress();
        if (!state.getHeap().has(address)) throw new MyException("Address not in Heap!");

        IValue r = this.value.eval(state.getSymTable(), state.getHeap());
        if (!v.getType().equals(new RefType(r.getType()))) throw new MyException("Missmatched Type! " + v.getType().toString() + " and " + (new RefType(r.getType())).toString());

        state.getHeap().set(address, r);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.get(var_name);
        IType typexp = value.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp))) return typeEnv;
        else throw new MyException("Heap Write stmt: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new heapWriteStmt(var_name, value.deepCopy());
    }

    @Override
    public String toString() {
        return "heapWrite(" + var_name + ", " + value.toString() + ")";
    }
}
