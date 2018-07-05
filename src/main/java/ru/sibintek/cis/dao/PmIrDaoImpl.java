package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.PmIrEntity;

import java.util.List;

@Component
public class PmIrDaoImpl implements PmIrDao {
    @Autowired
    private AbstractDao abstractDao;

    @Override
    public void delete(PmIrEntity psIr) {
        abstractDao.delete(psIr);
    }

    @Override
    public PmIrEntity getById(Long id) {
        return abstractDao.getById(PmIrEntity.class, id);
    }

    @Override
    public void save(PmIrEntity psIr, Long id) {
        abstractDao.save(psIr, id);
    }

    @Override
    public List<PmIrEntity> getByCriteria(Criterion criterion) {
        return abstractDao.getByCriteria(PmIrEntity.class, criterion);
    }

    @Override
    public List<PmIrEntity> getAll() {
        return abstractDao.getAll(PmIrEntity.class);
    }
}
