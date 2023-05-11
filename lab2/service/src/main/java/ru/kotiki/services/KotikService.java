package ru.kotiki.services;

import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.util.List;

public interface KotikService {

    Kotik findById(long id);

    List<Kotik> findAll();

    void save(Kotik kotik);

    void update(Kotik kotik);

    void delete(long id);

    Owner findOwner(long kotikId);

    List<Kotik> findFriends(long kotikId);

    boolean isFriends(long kotik1Id, long kotik2Id);

    void friend(long kotik1Id, long kotik2Id);

    void unfriend(long kotik1Id, long kotik2Id);
}
