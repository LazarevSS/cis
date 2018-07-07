package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmFuncAreaEntity;
import ru.sibintek.cis.model.dto.FunctionAndRelatedJoin;

import java.util.List;

public interface PmFuncAreaDAO {
    void delete(PmFuncAreaEntity pmFuncarea);

    PmFuncAreaEntity getById(int id);

    void save(PmFuncAreaEntity pmFuncarea, int id);

    List<PmFuncAreaEntity> getByCriteria(Criterion criterion);

    List<PmFuncAreaEntity> getAll();

    List<FunctionAndRelatedJoin> getFunctionAndRelatedJoins(Integer faId);
}
