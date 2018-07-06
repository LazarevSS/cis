package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmFuncareaEntity;

import java.util.List;

public interface PmFuncareaDao {
    void delete(PmFuncareaEntity pmFuncarea);

    PmFuncareaEntity getById(int id);

    void save(PmFuncareaEntity pmFuncarea, int id);

    List<PmFuncareaEntity> getByCriteria(Criterion criterion);

    List<PmFuncareaEntity> getAll();
}
