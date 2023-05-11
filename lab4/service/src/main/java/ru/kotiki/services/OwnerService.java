package ru.kotiki.services;

import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;

import java.util.List;

public interface OwnerService {

    OwnerDto findById(long id);
    List<OwnerDto> findAll();

    void save(OwnerDto ownerDto);

    void update(OwnerDto ownerDto);

    void delete(long id);

    List<KotikDto> findKotikiListByOwnerId(long id);
}
