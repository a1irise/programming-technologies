package ru.kotiki.dao;

import ru.kotiki.utils.HibernateUtil;
import ru.kotiki.entities.Owner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OwnerDaoImpl implements OwnerDao {

    @Override
    public Owner findById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Owner WHERE id=:id";
        Query<Owner> query = session.createQuery(hql, Owner.class);
        query.setParameter("id", id);
        Owner owner = query.getSingleResultOrNull();
        session.close();
        return owner;
    }

    @Override
    public List<Owner> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Owner";
        Query<Owner> query = session.createQuery(hql, Owner.class);
        List<Owner> owners = query.list();
        session.close();
        return owners;
    }

    @Override
    public void save(Owner owner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.merge(owner);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Owner owner = this.findById(id);
        session.remove(owner);
        session.getTransaction().commit();
        session.close();
    }
}
