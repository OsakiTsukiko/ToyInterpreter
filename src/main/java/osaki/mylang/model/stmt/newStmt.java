package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.exp.IExp;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.RefType;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.RefValue;

public class newStmt implements IStmt {
    String var_name;
    IExp exp;

    public newStmt(String var_name, IExp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IMyDictionary<String, IValue> sym_table = state.getSymTable();

        IValue v = exp.eval(sym_table, state.getHeap());
        if (sym_table.has(var_name)) {
            IValue in_tbl = sym_table.get(var_name);
            if (in_tbl.getType().equals(new RefType(v.getType()))) {
                int address = state.getHeap().nextAddress();
                state.getHeap().set(address, v);
                sym_table.set(var_name, new RefValue(address, v.getType()));

                return null;
            } else throw new MyException("Wrong Variable Type: " + in_tbl.getType().toString() + " should be " + (new RefType(v.getType())).toString());
        } else throw new MyException("Undefined variable: " + var_name);
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.get(var_name);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp))) return typeEnv;
        else throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new newStmt(var_name, exp.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + this.var_name + ", " + this.exp.toString() + ")";
    }
}
