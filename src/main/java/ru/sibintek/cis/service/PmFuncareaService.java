package ru.sibintek.cis.service;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmFuncareaEntity;

import java.util.List;

public interface PmFuncareaService {
    void delete(PmFuncareaEntity pmFuncarea);

    PmFuncareaEntity getById(Long id);

    void save(PmFuncareaEntity pmFuncarea, Long id);

    List<PmFuncareaEntity> getByCriteria(Criterion criterion);

    List<PmFuncareaEntity> getAll();
}
