package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.IntType;
import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.IntValue;

public class RelExp implements IExp {
    IExp e1;
    IExp e2;
    String op;

    public RelExp(IExp e1, IExp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl, IMyHeap<Integer, IValue> hp) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                if (op.equals("<")) return new BoolValue(i1.getVal() < i2.getVal());
                if (op.equals("<=")) return new BoolValue(i1.getVal() <= i2.getVal());
                if (op.equals("==")) return new BoolValue(i1.getVal() == i2.getVal());
                if (op.equals("!=")) return new BoolValue(i1.getVal() != i2.getVal());
                if (op.equals(">=")) return new BoolValue(i1.getVal() >= i2.getVal());
                if (op.equals(">")) return new BoolValue(i1.getVal() > i2.getVal());

                throw new MyException("Unknown Relational OPERAND!!1");
            } else {
                throw new MyException("Second Operand is not an INT!!1");
            }
        } else {
            throw new MyException("First Operand is not an INT!!1");
        }
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }

    @Override
    public IExp deepCopy() {
        return new RelExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
