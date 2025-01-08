package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public class VarExp implements IExp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl, IMyHeap<Integer, IValue> hp) throws MyException {
        return tbl.get(id);
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv.get(id);
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
