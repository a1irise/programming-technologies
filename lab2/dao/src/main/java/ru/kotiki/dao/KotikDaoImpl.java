package ru.kotiki.dao;

import ru.kotiki.utils.HibernateUtil;
import ru.kotiki.entities.Kotik;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

import java.util.List;

public class KotikDaoImpl implements KotikDao {

    @Override
    public Kotik findById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Kotik WHERE id=:id";
        Query<Kotik> query = session.createQuery(hql, Kotik.class);
        query.setParameter("id", id);
        Kotik kotik = query.getSingleResultOrNull();
        session.close();
        return kotik;
    }

    @Override
    public List<Kotik> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Kotik";
        Query<Kotik> query = session.createQuery(hql, Kotik.class);
        List<Kotik> kotiki = query.list();
        session.close();
        return kotiki;
    }

    @Override
    public void save(Kotik kotik) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(kotik);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Kotik kotik) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.merge(kotik);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Kotik kotik = this.findById(id);
        session.remove(kotik);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Kotik> findByOwnerId(long ownerId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Kotik WHERE ownerId=:id";
        Query<Kotik> query = session.createQuery(hql, Kotik.class);
        query.setParameter("id", ownerId);
        List<Kotik> kotiki = query.list();
        session.close();
        return kotiki;
    }
}
