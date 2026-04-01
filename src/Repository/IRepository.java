package Repository;

import Exceptions.MyException;
import Model.PrgState;

import java.util.List;

public interface IRepository
{
    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> prgList);

    void logPrgStateExec(PrgState prgState) throws MyException;
}
