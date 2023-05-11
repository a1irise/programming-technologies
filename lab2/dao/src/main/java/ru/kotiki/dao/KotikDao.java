package ru.kotiki.dao;

import ru.kotiki.entities.Kotik;

import java.util.List;

public interface KotikDao {

    Kotik findById(long id);

    List<Kotik> findAll();

    void save(Kotik kotik);

    void update(Kotik kotik);

    void delete(long id);

    List<Kotik> findByOwnerId(long ownerId);
}
