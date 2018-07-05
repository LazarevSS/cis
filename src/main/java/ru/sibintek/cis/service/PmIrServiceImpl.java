package ru.sibintek.cis.service;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.PmIrDao;
import ru.sibintek.cis.model.PmIrEntity;

import java.util.List;

@Service
public class PmIrServiceImpl implements PmIrService {
    @Autowired
    private PmIrDao dao;

    @Override
    public void delete(PmIrEntity pmIr) {
        dao.delete(pmIr);
    }

    @Override
    public PmIrEntity getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void save(PmIrEntity pmIr, Long id) {
        dao.save(pmIr, id);
    }

    @Override
    public List<PmIrEntity> getByCriteria(Criterion criterion) {
        return dao.getByCriteria(criterion);
    }

    @Override
    public List<PmIrEntity> getAll() {
        return dao.getAll();
    }
}
