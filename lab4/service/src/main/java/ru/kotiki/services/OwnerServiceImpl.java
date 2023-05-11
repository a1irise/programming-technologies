package ru.kotiki.services;

import ru.kotiki.dao.KotikRepository;
import ru.kotiki.dao.OwnerRepository;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.Mapper;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final KotikRepository kotikRepository;
    private final OwnerRepository ownerRepository;
    private final Mapper mapper;

    @Autowired
    public OwnerServiceImpl(KotikRepository kotikRepository, OwnerRepository ownerRepository, Mapper mapper) {
        this.kotikRepository = kotikRepository;
        this.ownerRepository = ownerRepository;
        this.mapper = mapper;
    }

    @Override
    public OwnerDto findById(long id) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        return (owner == null) ? null : mapper.toOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> findAll() {
        return ownerRepository.findAll()
                .stream()
                .map(mapper::toOwnerDto)
                .toList();
    }

    @Override
    public void save(OwnerDto ownerDto) {
        Owner owner = mapper.toOwner(ownerDto);
        ownerRepository.save(owner);
    }

    @Override
    public void update(OwnerDto ownerDto) {
        Owner owner = mapper.toOwner(ownerDto);
        ownerRepository.save(owner);
    }

    @Override
    public void delete(long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public List<KotikDto> findKotikiListByOwnerId(long id) {
        return kotikRepository.findByOwnerId(id)
                .stream()
                .map(mapper::toKotikDto)
                .toList();
    }
}
