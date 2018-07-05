package ru.sibintek.cis.service;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.List;

public interface PmIsService {
    void delete(PmIsEntity psIs);

    PmIsEntity getById(Long id);

    void save(PmIsEntity psIs, Long id);

    List<PmIsEntity> getByCriteria(Criterion criterion);

    List<PmIsEntity> getAll();
}
