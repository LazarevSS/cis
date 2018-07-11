package ru.sibintek.cis.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.utils.HibernateSessionFactory;

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

    public List<PmIrEntity> getJoinWithPmIs(Long pmIS_id) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(PmIrEntity.class)
                .add(Restrictions.eq("fkIsId", pmIS_id));
        List<PmIrEntity> result = criteria.list();
        session.getTransaction().commit();
        return result;
    }
}
