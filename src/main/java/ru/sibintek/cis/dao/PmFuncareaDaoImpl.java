package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.PmFuncareaEntity;

import java.util.List;

@Component
public class PmFuncareaDaoImpl implements PmFuncareaDao {
    @Autowired
    private AbstractDao abstractDao;

    @Override
    public void delete(PmFuncareaEntity pmFuncarea) {
        abstractDao.delete(pmFuncarea);
    }

    @Override
    public PmFuncareaEntity getById(Long id) {
        return abstractDao.getById(PmFuncareaEntity.class, id);
    }

    @Override
    public void save(PmFuncareaEntity pmFuncarea, Long id) {
        abstractDao.save(pmFuncarea, id);
    }

    @Override
    public List<PmFuncareaEntity> getByCriteria(Criterion criterion) {
        return abstractDao.getByCriteria(PmFuncareaEntity.class, criterion);
    }

    @Override
    public List<PmFuncareaEntity> getAll() {
        return abstractDao.getAll(PmFuncareaEntity.class);
    }
}
