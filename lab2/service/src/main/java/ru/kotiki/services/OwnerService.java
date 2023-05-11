package ru.kotiki.services;

import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.util.List;

public interface OwnerService {

    Owner findById(Long id);

    List<Owner> findAll();

    void save(Owner owner);

    void update(Owner owner);

    void delete(long id);

    List<Kotik> findKotiki(long ownerId);
}
