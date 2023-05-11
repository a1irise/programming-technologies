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
        Kotik kotik = kotikRepository.findById(id).orElseThrow();
        return mapper.toKotikDto(kotik);
    }

    @Override
    public List<KotikDto> findAll() {
        return kotikRepository.findAll()
                .stream()
                .map(mapper::toKotikDto)
                .toList();
    }

    @Override
    public KotikDto save(KotikDto kotikDto) {
        return mapper.toKotikDto(kotikRepository.save(mapper.toKotik(kotikDto)));
    }

    @Override
    public void delete(long id) {
        kotikRepository.deleteById(id);
    }

    @Override
    public OwnerDto findOwner(long kotikId) {
        Kotik kotik = kotikRepository.findById(kotikId).orElseThrow();
        Owner owner = ownerRepository.findById(kotik.getOwnerId()).orElseThrow();
        return mapper.toOwnerDto(owner);
    }

    @Override
    public List<KotikDto> findByColor(Color color) {
        return kotikRepository.findByColor(color)
                .stream()
                .map(mapper::toKotikDto)
                .toList();
    }

    @Override
    public List<KotikDto> findFriends(long kotikId) {
        List<KotikDto> kotiki = new ArrayList<>();
        List<Friends> friends = friendsRepository.findAllByKotikId(kotikId);
        for (Friends friend : friends) {
            long friendId = Objects.equals(friend.getKotik1Id(), kotikId) ? friend.getKotik2Id() : friend.getKotik1Id();
            Kotik kotik = kotikRepository.findById(friendId).orElseThrow();
            KotikDto kotikDto = mapper.toKotikDto(kotik);
            kotiki.add(kotikDto);
        }
        return kotiki;
    }

    @Override
    public void friend(long kotik1Id, long kotik2Id) {
        if (friendsRepository.findByKotikiId(kotik1Id, kotik2Id) == null) {
            Friends friends = new Friends(kotik1Id, kotik2Id);
            friendsRepository.save(friends);
        }
    }

    @Override
    public void unfriend(long kotik1Id, long kotik2Id) {
        if (friendsRepository.findByKotikiId(kotik1Id, kotik2Id) != null) {
            Friends friends = friendsRepository.findByKotikiId(kotik1Id, kotik2Id);
            friendsRepository.delete(friends);
        }
    }
}
