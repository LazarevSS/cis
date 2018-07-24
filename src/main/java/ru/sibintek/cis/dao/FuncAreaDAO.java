package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.FuncAreaModel;
import ru.sibintek.cis.model.dto.FunctionAndRelatedJoin;

import java.util.List;

public interface FuncAreaDAO {
    void delete(FuncAreaModel pmFuncarea);

    FuncAreaModel getById(int id);

    void save(FuncAreaModel pmFuncarea, int id);

    List<FuncAreaModel> getAll();

    List<FunctionAndRelatedJoin> getFunctionAndRelatedJoins(Integer faId);
}
