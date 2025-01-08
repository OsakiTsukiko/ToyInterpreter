package osaki.mylang.model.types;

import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IValue;

public class BoolType implements IType {
    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }
}
