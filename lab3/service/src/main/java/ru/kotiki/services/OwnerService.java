package ru.kotiki.services;

import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;

import java.util.List;

public interface OwnerService {

    OwnerDto findById(long id);

    List<OwnerDto> findAll();

    OwnerDto save(OwnerDto ownerDto);

    void delete(long id);

    List<KotikDto> findKotikiByOwnerId(long id);
}
