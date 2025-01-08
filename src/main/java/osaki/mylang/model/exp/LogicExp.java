package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.BoolType;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.values.BoolValue;
import osaki.mylang.model.values.IValue;

public class LogicExp implements IExp {
    IExp e1;
    IExp e2;
    LOGIC_OPERATOR op;
    String op_string;

    public LogicExp(IExp e1, IExp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        switch (op) {
            case "or" -> this.op = LOGIC_OPERATOR.OR;
            case "and" -> this.op = LOGIC_OPERATOR.AND;
            default -> throw new IllegalStateException("Unexpected value: " + op);
        }
        this.op_string = op;
    }

    @Override
    public IValue eval(IMyDictionary<String, IValue> tbl, IMyHeap<Integer, IValue> hp) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                switch (op) {
                    case AND -> {
                        if (b1.getVal() && b2.getVal()) return new BoolValue(true);
                        else return new BoolValue(false);
                    }
                    case OR -> {
                        if (b1.getVal() || b2.getVal()) return new BoolValue(true);
                        else return new BoolValue(false);
                    }
                }
            } else {
                throw new MyException("Second Operand is not an BOOL!!1");
            }
        } else {
            throw new MyException("First Operand is not an BOOL!!1");
        }
        throw new MyException("UNREACHABLE!!1");
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else throw new MyException("second operand is not a boolean");
        } else throw new MyException("first operand is not an boolean");
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), op_string);
    }


    public enum LOGIC_OPERATOR {
        AND,
        OR
    }

    @Override
    public String toString() {
        return this.e1.toString() + " " + this.op_string + " " + this.e2.toString();
    }
}
