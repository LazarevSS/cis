package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmIrEntity;

import java.util.List;

public class PmIrDao extends AbstractDao {
    public void delete(PmIrEntity psIr) {
        super.delete(psIr);
    }

    public PmIrEntity getById(Long id) {
        return super.getById(PmIrEntity.class, id);
    }

    public void save(PmIrEntity psIr, Long id) {
        super.save(psIr, id);
    }

    public List<PmIrEntity> getByCriteria(Criterion criterion) {
        return super.getByCriteria(PmIrEntity.class, criterion);
    }

    public List<PmIrEntity> getAll() {
        return super.getAll(PmIrEntity.class);
    }
}
