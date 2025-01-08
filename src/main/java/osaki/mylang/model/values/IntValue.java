package osaki.mylang.model.values;

import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.IntType;

public class IntValue implements IValue {
    int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object another){
        if (!(another instanceof IntValue a)) return false;
        return this.val == a.val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
