package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmFuncareaEntity;

import java.util.List;

public class PmFuncareaDao extends AbstractDao {
    public void delete(PmFuncareaEntity pmFuncarea) {
        super.delete(pmFuncarea);
    }

    public PmFuncareaEntity getById(Long id) {
        return super.getById(PmFuncareaEntity.class, id);
    }

    public void save(PmFuncareaEntity pmFuncarea, Long id) {
        super.save(pmFuncarea, id);
    }

    public List<PmFuncareaEntity> getByCriteria(Criterion criterion) {
        return super.getByCriteria(PmFuncareaEntity.class, criterion);
    }

    public List<PmFuncareaEntity> getAll() {
        return super.getAll(PmFuncareaEntity.class);
    }
}
