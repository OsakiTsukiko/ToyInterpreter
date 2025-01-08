package osaki.mylang.model.values;

import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.RefType;

public class RefValue implements IValue {
    int address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + this.address + ": " + this.locationType.toString() + ")";
    }
}
