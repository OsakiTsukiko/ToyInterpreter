package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public class ValueExp implements IExp {
    IValue v;

    public ValueExp(IValue v) {
        this.v = v;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl, IMyHeap<Integer, IValue> hp) throws MyException {
        return v;
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        return v.getType();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(this.v.deepCopy());
    }

    @Override
    public String toString() {
        return v.toString();
    }
}
