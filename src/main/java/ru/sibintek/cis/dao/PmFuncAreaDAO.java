package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.PmFuncAreaEntity;
import ru.sibintek.cis.model.dto.FunctionAndRelatedJoin;

import java.util.List;

public interface PmFuncAreaDAO {
    void delete(PmFuncAreaEntity pmFuncarea);

    PmFuncAreaEntity getById(int id);

    void save(PmFuncAreaEntity pmFuncarea, int id);

    List<PmFuncAreaEntity> getAll();

    List<FunctionAndRelatedJoin> getFunctionAndRelatedJoins(Integer faId);
}
