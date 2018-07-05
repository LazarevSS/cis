package ru.sibintek.cis.service;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.PmFuncareaDao;
import ru.sibintek.cis.model.PmFuncareaEntity;

import java.util.List;

@Service
public class PmFuncareaServiceImpl implements PmFuncareaService {
    @Autowired
    private PmFuncareaDao dao;
    @Override
    public void delete(PmFuncareaEntity pmFuncarea) {
        dao.delete(pmFuncarea);
    }

    @Override
    public PmFuncareaEntity getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public void save(PmFuncareaEntity pmFuncarea, Long id) {
        dao.save(pmFuncarea, id);
    }

    @Override
    public List<PmFuncareaEntity> getByCriteria(Criterion criterion) {
        return dao.getByCriteria(criterion);
    }

    @Override
    public List<PmFuncareaEntity> getAll() {
        return dao.getAll();
    }
}
