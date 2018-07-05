package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.List;

@Component
public class PmIsDaoImpl implements PmIsDao {
    @Autowired
    private AbstractDao abstractDao;

    @Override
    public void delete(PmIsEntity psIs) {
        abstractDao.delete(psIs);
    }

    @Override
    public PmIsEntity getById(Long id) {
        return abstractDao.getById(PmIsEntity.class, id);
    }

    @Override
    public void save(PmIsEntity psIs, Long id) {
        abstractDao.save(psIs, id);
    }

    @Override
    public List<PmIsEntity> getByCriteria(Criterion criterion) {
        return abstractDao.getByCriteria(PmIsEntity.class, criterion);
    }

    @Override
    public List<PmIsEntity> getAll() {
        return abstractDao.getAll(PmIsEntity.class);
    }
}
