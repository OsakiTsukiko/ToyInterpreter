package osaki.mylang.model.stmt;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.adts.IMyDictionary;
import osaki.mylang.model.exceptions.MyException;
import osaki.mylang.model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IMyDictionary<String, IType> typecheck(IMyDictionary<String,IType> typeEnv) throws MyException;
    IStmt deepCopy();
}
