package ru.kotiki.dao;

import ru.kotiki.entities.Owner;

import java.util.List;

public interface OwnerDao {

    Owner findById(long id);

    List<Owner> findAll();

    void save(Owner owner);

    void update(Owner owner);

    void delete(long id);
}
