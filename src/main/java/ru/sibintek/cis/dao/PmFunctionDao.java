package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.PmFunctionEntity;

import java.util.List;

@Component
public class PmFunctionDao extends AbstractDao {
    public void delete(PmFunctionEntity pmFunction) {
        super.delete(pmFunction);
    }

    public PmFunctionEntity getById(Long id) {
        return super.getById(PmFunctionEntity.class, id);
    }

    public void save(PmFunctionEntity pmFunction, Long id) {
        super.save(pmFunction, id);
    }

    public List<PmFunctionEntity> getByCriteria(Criterion criterion) {
        return super.getByCriteria(PmFunctionEntity.class, criterion);
    }

    public List<PmFunctionEntity> getAll() {
        return super.getAll(PmFunctionEntity.class);
    }

}
