package ru.sibintek.cis.service;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmIrEntity;

import java.util.List;

public interface PmIrService {
    void delete(PmIrEntity psIs);

    PmIrEntity getById(Long id);

    void save(PmIrEntity psIs, Long id);

    List<PmIrEntity> getByCriteria(Criterion criterion);

    List<PmIrEntity> getAll();
}
