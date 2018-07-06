package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmIrEntity;


import java.util.List;

public interface PmIrDAO {
    void delete(PmIrEntity psIr);

    PmIrEntity getById(int id);

    void save(PmIrEntity psIr, int id);

    List<PmIrEntity> getByCriteria(Criterion criterion);

    List<PmIrEntity> getAll();
}
