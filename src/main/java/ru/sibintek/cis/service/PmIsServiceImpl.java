package ru.sibintek.cis.service;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.PmIsDao;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.List;

@Service
public class PmIsServiceImpl implements PmIsService {
    @Autowired
    private PmIsDao dao;

    @Override
    public void delete(PmIsEntity psIs) {
        dao.delete(psIs);
    }

    @Override
    public PmIsEntity getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void save(PmIsEntity psIs, Long id) {
        dao.save(psIs, id);
    }

    @Override
    public List<PmIsEntity> getByCriteria(Criterion criterion) {
        return dao.getByCriteria(criterion);
    }

    @Override
    public List<PmIsEntity> getAll() {
        return dao.getAll();
    }
}
