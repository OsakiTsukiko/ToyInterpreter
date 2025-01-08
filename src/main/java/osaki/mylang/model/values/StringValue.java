package osaki.mylang.model.values;

import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.StringType;

public class StringValue implements IValue {
    String val;

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object another){
        if (!(another instanceof StringValue a)) return false;
        return this.val.equals(a.val);
    }

    @Override
    public String toString() {
        return val;
    }
}
