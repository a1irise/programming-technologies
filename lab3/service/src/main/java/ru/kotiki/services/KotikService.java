package ru.kotiki.services;

import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Color;

import java.util.List;

public interface KotikService {

    KotikDto findById(long id);

    List<KotikDto> findAll();

    KotikDto save(KotikDto kotikDto);

    void delete(long id);

    OwnerDto findOwner(long kotikId);

    List<KotikDto> findByColor(Color color);

    List<KotikDto> findFriends(long kotikId);

    void friend(long kotik1Id, long kotik2Id);

    void unfriend(long kotik1Id, long kotik2Id);
}
