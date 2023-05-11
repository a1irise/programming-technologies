package ru.kotiki.services;

import ru.kotiki.dao.KotikRepository;
import ru.kotiki.dao.OwnerRepository;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.Mapper;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final KotikRepository kotikRepository;
    private final Mapper mapper;

    @Autowired
    public OwnerServiceImpl(OwnerRepository ownerRepository, KotikRepository kotikRepository, Mapper mapper) {
        this.ownerRepository = ownerRepository;
        this.kotikRepository = kotikRepository;
        this.mapper = mapper;
    }

    @Override
    public OwnerDto findById(long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow();
        return mapper.toOwnerDto(owner);
    }

    @Override
    public List<OwnerDto> findAll() {
        return ownerRepository.findAll()
                .stream()
                .map(mapper::toOwnerDto)
                .toList();
    }

    @Override
    public OwnerDto save(OwnerDto ownerDto) {
        return mapper.toOwnerDto(ownerRepository.save(mapper.toOwner(ownerDto)));
    }

    @Override
    public void delete(long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public List<KotikDto> findKotikiByOwnerId(long id) {
        return kotikRepository.findByOwnerId(id)
                .stream()
                .map(mapper::toKotikDto)
                .toList();
    }
}
