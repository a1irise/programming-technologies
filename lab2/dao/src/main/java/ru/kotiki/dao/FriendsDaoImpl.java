package ru.kotiki.dao;

import ru.kotiki.entities.Friends;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.kotiki.utils.HibernateUtil;

import java.util.List;

public class FriendsDaoImpl implements FriendsDao {

    @Override
    public Friends findByKotikiId(long kotik1Id, long kotik2Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Friends WHERE (kotik1Id=:id1 and kotik2Id=:id2) or (kotik1Id=:id2 and kotik2Id=:id1)";
        Query<Friends> query = session.createQuery(hql, Friends.class);
        query.setParameter("id1", kotik1Id);
        query.setParameter("id2", kotik2Id);
        Friends friends = query.getSingleResultOrNull();
        session.close();
        return friends;
    }

    @Override
    public List<Friends> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Friends";
        Query<Friends> query = session.createQuery(hql, Friends.class);
        List<Friends> friends = query.list();
        session.close();
        return friends;
    }

    @Override
    public void save(Friends friends) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.persist(friends);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(long kotik1Id, long kotik2Id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Friends friends = this.findByKotikiId(kotik1Id, kotik2Id);
        session.remove(friends);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Friends> findByKotikId(long kotikId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Friends WHERE kotik1Id=:id or kotik2Id=:id";
        Query<Friends> query = session.createQuery(hql, Friends.class);
        query.setParameter("id", kotikId);
        List<Friends> friends = query.list();
        session.close();
        return friends;
    }
}
