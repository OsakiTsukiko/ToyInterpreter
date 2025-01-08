package osaki.mylang.model.values;

import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IType;

public class BoolValue implements IValue {
    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object another){
        if (!(another instanceof BoolValue a)) return false;
        return this.val == a.val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
