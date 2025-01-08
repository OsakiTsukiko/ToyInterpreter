package osaki.mylang.model.types;

import osaki.mylang.model.values.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
}
