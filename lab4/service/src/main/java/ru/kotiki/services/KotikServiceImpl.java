package ru.kotiki.services;

import ru.kotiki.dao.FriendsRepository;
import ru.kotiki.dao.KotikRepository;
import ru.kotiki.dao.OwnerRepository;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.Mapper;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Friends;
import ru.kotiki.entities.Kotik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kotiki.entities.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class KotikServiceImpl implements KotikService {

    private final KotikRepository kotikRepository;
    private final OwnerRepository ownerRepository;
    private final FriendsRepository friendsRepository;
    private final Mapper mapper;

    @Autowired
    public KotikServiceImpl(
            KotikRepository kotikRepository,
            OwnerRepository ownerRepository,
            FriendsRepository friendsRepository,
            Mapper mapper) {
        this.kotikRepository = kotikRepository;
        this.ownerRepository = ownerRepository;
        this.friendsRepository = friendsRepository;
        this.mapper = mapper;
    }

    @Override
    public KotikDto findById(long id) {
        Kotik kotik = kotikRepository.findById(id).orElse(null);
        return (kotik == null) ? null : mapper.toKotikDto(kotik);
    }

    @Override
    public List<KotikDto> findAll() {
        return kotikRepository.findAll()
                .stream()
                .map(mapper::toKotikDto)
                .toList();
    }

    @Override
    public void save(KotikDto kotikDto) {
        Kotik kotik = mapper.toKotik(kotikDto);
        kotikRepository.save(kotik);
    }

    @Override
    public void update(KotikDto kotikDto) {
        Kotik kotik = mapper.toKotik(kotikDto);
        kotikRepository.save(kotik);
    }

    @Override
    public void delete(long id) {
        kotikRepository.deleteById(id);
    }

    @Override
    public List<KotikDto> findKotikiByColor(Color color) {
        List<KotikDto> kotiki = this.findAll();
        List<KotikDto> finalList = new ArrayList<>();
        for (KotikDto kotikDto : kotiki) {
            if (kotikDto.getColor() == color) {
                finalList.add(kotikDto);
            }
        }
        return finalList;
    }

    @Override
    public OwnerDto findOwnerByKotikId(long id) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        return (owner == null) ? null : mapper.toOwnerDto(owner);
    }

    @Override
    public List<KotikDto> findFriendsByKotikId(long id) {
        List<KotikDto> kotiki = new ArrayList<>();
        List<Friends> friends = friendsRepository.findAllByKotikId(id);
        for (Friends friend : friends) {
            long friendId = Objects.equals(friend.getKotik1Id(), id) ? friend.getKotik2Id() : friend.getKotik1Id();
            KotikDto kotikDto = findById(friendId);
            kotiki.add(kotikDto);
        }
        return kotiki;
    }

    @Override
    public boolean isFriends(long kotik1Id, long kotik2Id) {
        return friendsRepository.findByKotikiId(kotik1Id, kotik2Id) != null;
    }

    @Override
    @Transactional
    public void friend(long kotik1Id, long kotik2Id) {
        if (!isFriends(kotik1Id, kotik2Id)) {
            Friends friends = new Friends(kotik1Id, kotik2Id);
            friendsRepository.save(friends);
        }
    }

    @Override
    @Transactional
    public void unfriend(long kotik1Id, long kotik2Id) {
        if (isFriends(kotik1Id, kotik2Id)) {
            Friends friends = friendsRepository.findByKotikiId(kotik1Id, kotik2Id);
            friendsRepository.delete(friends);
        }
    }
}
