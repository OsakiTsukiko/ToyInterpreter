package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public class VarDeclStmt implements IStmt {
    private String name;
    private IType type;

    public VarDeclStmt(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (state.getSymTable().has(name)) {
            throw new MyException("Variable " + name + " is already declared.");
        }

        IValue defaultValue = type.defaultValue();
        state.getSymTable().set(name, defaultValue);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.set(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
