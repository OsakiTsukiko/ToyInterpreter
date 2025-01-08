package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.IValue;

public interface IExp {
    IValue eval(IMyDictionary<String,IValue> tbl, IMyHeap<Integer, IValue> hp) throws MyException;
    IType typecheck(IMyDictionary<String,IType> typeEnv) throws MyException;

    IExp deepCopy();
}
