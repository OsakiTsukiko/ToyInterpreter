package osaki.mylang.model.exp;

import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.adts.IMyHeap;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;
import osaki.mylang.model.types.IntType;
import osaki.mylang.model.values.IValue;
import osaki.mylang.model.values.IntValue;

public class ArithExp implements IExp {
    IExp e1;
    IExp e2;
    OPERATOR op;
    String op_string;

    public ArithExp(IExp e1, IExp e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        switch (op) {
            case "+" -> this.op = OPERATOR.PLUS;
            case "-" -> this.op = OPERATOR.MINUS;
            case "*" -> this.op = OPERATOR.STAR;
            case "/" -> this.op = OPERATOR.DIVIDE;
            default -> this.op = OPERATOR.PLUS;
        }
        this.op_string = op;
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
                int n1 = i1.getVal();
                int n2 = i2.getVal();
                if (op == OPERATOR.PLUS) return new IntValue(n1 + n2);
                if (op == OPERATOR.MINUS) return new IntValue(n1 - n2);
                if (op == OPERATOR.STAR) return new IntValue(n1 * n2);
                if (op == OPERATOR.DIVIDE) {
                    if (n1 == 0) {
                        throw new MyException("Division by ZERO!!1");
                    }
                    return new IntValue(n1 / n2);
                }
            } else {
                throw new MyException("Second Operand is not an INTEGER!!1");
            }
        } else {
            throw new MyException("First Operand is not an INTEGER!!1");
        }
        throw new MyException("Unreachable!");
    }

    @Override
    public IType typecheck(IMyDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
            return new IntType();
            } else throw new MyException("second operand is not an integer");
        } else throw new MyException("first operand is not an integer");
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op_string);
    }

    public enum OPERATOR {
        PLUS,
        MINUS,
        STAR,
        DIVIDE
    }

    @Override
    public String toString() {
        return this.e1.toString() + " " + op_string + " " + this.e2.toString();
    }
}
