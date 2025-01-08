package osaki.mylang.repository;

import osaki.mylang.model.PrgState;
import osaki.mylang.model.exceptions.MyException;

import java.util.List;

public interface IRepo {
    void logPrgStateExec(PrgState state) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> new_state_list);
}
