package ru.kotiki.services;

import ru.kotiki.dao.FriendsDao;
import ru.kotiki.dao.KotikDao;
import ru.kotiki.dao.OwnerDao;
import ru.kotiki.entities.Friends;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KotikServiceImpl implements KotikService {

    private final KotikDao kotikDao;
    private final OwnerDao ownerDao;
    private final FriendsDao friendsDao;

    public KotikServiceImpl(KotikDao kotikDao, OwnerDao ownerDao, FriendsDao friendsDao) {
        this.kotikDao = kotikDao;
        this.ownerDao = ownerDao;
        this.friendsDao = friendsDao;
    }

    @Override
    public Kotik findById(long id) {
        return kotikDao.findById(id);
    }

    @Override
    public List<Kotik> findAll() {
        return kotikDao.findAll();
    }

    @Override
    public void save(Kotik kotik) {
        kotikDao.save(kotik);
    }

    @Override
    public void update(Kotik kotik) {
        kotikDao.update(kotik);
    }

    @Override
    public void delete(long id) {
        kotikDao.delete(id);
    }

    @Override
    public Owner findOwner(long kotikId) {
        Kotik kotik = kotikDao.findById(kotikId);
        return ownerDao.findById(kotik.getOwnerId());
    }

    @Override
    public List<Kotik> findFriends(long kotikId) {
        List<Kotik> kotiki = new ArrayList<>();
        List<Friends> friends = friendsDao.findByKotikId(kotikId);
        for (Friends friend : friends) {
            long friendId = Objects.equals(friend.getKotik1Id(), kotikId)
                    ? friend.getKotik2Id()
                    : friend.getKotik1Id();
            Kotik kotik = kotikDao.findById(friendId);
            kotiki.add(kotik);
        }
        return kotiki;
    }

    @Override
    public boolean isFriends(long kotik1Id, long kotik2Id) {
        return friendsDao.findByKotikiId(kotik1Id, kotik2Id) != null;
    }

    @Override
    public void friend(long kotik1Id, long kotik2Id) {
        if (!isFriends(kotik1Id, kotik2Id)) {
            Friends friends = new Friends(kotik1Id, kotik2Id);
            friendsDao.save(friends);
        }
    }

    @Override
    public void unfriend(long kotik1Id, long kotik2Id) {
        if (isFriends(kotik1Id, kotik2Id)) {
            friendsDao.delete(kotik1Id, kotik2Id);
        }
    }
}
