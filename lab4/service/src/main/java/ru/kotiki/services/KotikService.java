package ru.kotiki.services;

import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Color;

import java.util.List;

public interface KotikService {

    KotikDto findById(long id);
    List<KotikDto> findAll();

    void save(KotikDto kotikDto);
    void update(KotikDto kotikDto);
    void delete(long id);

    List<KotikDto> findKotikiByColor(Color color);

    OwnerDto findOwnerByKotikId(long id);

    List<KotikDto> findFriendsByKotikId(long id);
    boolean isFriends(long kotik1Id, long kotik2Id);
    void friend(long kotik1Id, long kotik2Id);
    void unfriend(long kotik1Id, long kotik2Id);
}
