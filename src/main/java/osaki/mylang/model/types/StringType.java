package osaki.mylang.model.types;

import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }
}
