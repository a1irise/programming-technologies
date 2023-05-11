package ru.kotiki.services;

import ru.kotiki.dao.KotikDao;
import ru.kotiki.dao.OwnerDao;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.util.List;

public class OwnerServiceImpl implements OwnerService {

    private final OwnerDao ownerDao;
    private final KotikDao kotikDao;

    public OwnerServiceImpl(OwnerDao ownerDao, KotikDao kotikDao) {
        this.ownerDao = ownerDao;
        this.kotikDao = kotikDao;
    }

    @Override
    public Owner findById(Long id) {
        return ownerDao.findById(id);
    }

    @Override
    public List<Owner> findAll() {
        return ownerDao.findAll();
    }

    @Override
    public void save(Owner owner) {
        ownerDao.save(owner);
    }

    @Override
    public void update(Owner owner) {
        ownerDao.update(owner);
    }

    @Override
    public void delete(long ownerId) {
        ownerDao.delete(ownerId);
    }

    @Override
    public List<Kotik> findKotiki(long ownerId) {
        return kotikDao.findByOwnerId(ownerId);
    }
}
