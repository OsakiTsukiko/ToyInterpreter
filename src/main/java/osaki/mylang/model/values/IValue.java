package osaki.mylang.model.values;

import osaki.mylang.model.types.IType;

public interface IValue {
    IType getType();
    IValue deepCopy();
}
