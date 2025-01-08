package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.RefType;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.RefValue;

public class ReadHeapExp implements IExp {
    IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl, IMyHeap<Integer, IValue> hp) throws MyException {
        IValue v = exp.eval(tbl, hp);
        if (v instanceof RefValue r) {
            Integer address = r.getAddress();
            if (hp.has(address)) {
                return hp.get(address);
            } else throw new MyException("Address " + address.toString() + " not in Heap!");
        } else throw new MyException("Exp value not instance of RefValue!");
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType reft) {
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "heapRead(" + this.exp.toString() + ")";
    }
}
