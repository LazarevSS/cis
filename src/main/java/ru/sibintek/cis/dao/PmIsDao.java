package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.List;

public class PmIsDao extends AbstractDao {

    public void delete(PmIsEntity psIs) {
        super.delete(psIs);
    }

    public PmIsEntity getById(Long id) {
        return super.getById(PmIsEntity.class, id);
    }

    public void save(PmIsEntity psIs, Long id) {
        super.save(psIs, id);
    }

    public List<PmIsEntity> getByCriteria(Criterion criterion) {
        return super.getByCriteria(PmIsEntity.class, criterion);
    }

    public List<PmIsEntity> getAll() {
        return super.getAll(PmIsEntity.class);
    }
}
