package ru.sibintek.cis.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.utils.HibernateSessionFactory;

import java.util.List;

@Component
public class AbstractDao {
    public <T> void delete(final T o) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.delete(o);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.beginTransaction().rollback();
        }
    }

    public <T> T getById(final Class<T> type, final Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            T o = (T) session.get(type, id);
            session.getTransaction().commit();
            return o;
        } catch (RuntimeException e) {
            session.beginTransaction().rollback();
            return null;
        }
    }

    public <T> void save(final T o, final Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            if (id == null) {
                session.save(o);
            } else {
                session.update(o);
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.beginTransaction().rollback();
        }
    }

    public <T> List<T> getByCriteria(final Class<T> type, Criterion criterion) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            final Criteria criteria = session.createCriteria(type);
            criteria.add(criterion);
            List<T> result = criteria.list();
            session.getTransaction().commit();
            return result;
        } catch (RuntimeException e) {
            session.beginTransaction().rollback();
            return null;
        }
    }

    public <T> List<T> getAll(final Class<T> type) {
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            final Criteria criteria = session.createCriteria(type);
            List<T> result = criteria.list();
            session.getTransaction().commit();
            return result;
        } catch (RuntimeException e) {
            return null;
        }
    }

}
