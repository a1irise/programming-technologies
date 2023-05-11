package ru.kotiki.dao;

import ru.kotiki.entities.Friends;

import java.util.List;

public interface FriendsDao {

    Friends findByKotikiId(long kotik1Id, long kotik2Id);

    List<Friends> findAll();

    void save(Friends friends);

    void delete(long kotik1Id, long kotik2Id);

    List<Friends> findByKotikId(long kotikId);

}
