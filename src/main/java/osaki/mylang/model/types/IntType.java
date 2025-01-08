package osaki.mylang.model.types;

import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.IntValue;

public class IntType implements IType {
    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }
}
