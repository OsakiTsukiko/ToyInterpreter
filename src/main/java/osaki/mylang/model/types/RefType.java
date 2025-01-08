package osaki.mylang.model.types;

import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.RefValue;

public class RefType implements IType {
    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof RefType) {
            return this.inner.equals(((RefType) another).getInner());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ref(" + this.inner.toString() + ")";
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, this.inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner.deepCopy());
    }
}
