package ru.kotiki.services;

import ru.kotiki.dao.FriendsRepository;
import ru.kotiki.dao.KotikRepository;
import ru.kotiki.dao.OwnerRepository;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.Mapper;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Friends;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class KotikServiceTest {

    @Mock
    KotikRepository kotikRepository;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    FriendsRepository friendsRepository;
    Mapper mapper = new Mapper();


    KotikServiceImpl kotikService;
    MockitoSession session;

    @BeforeEach
    public void beforeEach() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
        kotikService = new KotikServiceImpl(kotikRepository, ownerRepository, friendsRepository, mapper);
    }

    @AfterEach
    public void afterEach() {
        session.finishMocking();
    }

    Owner owner1;
    Owner owner2;
    Owner owner3;

    Kotik kotik1;
    Kotik kotik2;
    Kotik kotik3;
    Kotik kotik4;

    Friends friends1;
    Friends friends2;
    Friends friends3;
    Friends friends4;

    {
        owner1 = new Owner("Gary", LocalDate.of(1, 2, 3));
        owner1.setId(1L);
        kotik1 = new Kotik("Milo", LocalDate.of(1, 2, 3), "stray", Color.BLACK, 1L);
        kotik1.setId(1L);
        kotik2 = new Kotik("Ralf", LocalDate.of(1, 2, 3), "british", Color.GINGER, 1L);
        kotik2.setId(2L);

        owner2 = new Owner("Jason", LocalDate.of(1, 2, 3));
        owner2.setId(2L);
        kotik3 = new Kotik("Bella", LocalDate.of(1, 2, 3), "scottish", Color.CREAM, 2L);
        kotik1.setId(3L);

        owner3 = new Owner("Jessica", LocalDate.of(1, 2, 3));
        owner3.setId(3L);
        kotik4 = new Kotik("Coco", LocalDate.of(1, 2, 3), "stray", Color.WHITE, 2L);
        kotik1.setId(4L);

        friends1 = new Friends(3L, 1L);
        friends1.setId(1L);
        friends2 = new Friends(2L, 3L);
        friends2.setId(2L);
        friends3 = new Friends(4L, 3L);
        friends3.setId(3L);
        friends4 = new Friends(1L, 4L);
        friends3.setId(4L);
    }

    @Test
    public void testFindById() {
        Mockito.when(kotikRepository.findById(1L)).thenReturn(Optional.of(kotik1));
        KotikDto kotik = kotikService.findById(1L);

        assertNotNull(kotik);
        assertEquals(kotik.getId(), kotik1.getId());
    }

    @Test
    public void testFindAll() {
        List<Kotik> expected = List.of(kotik1, kotik2, kotik3, kotik4);
        Mockito.when(kotikRepository.findAll()).thenReturn(expected);

        List<KotikDto> kotiki = kotikService.findAll();

        assertEquals(kotiki.size(), expected.size());
        for (int i = 0; i < kotiki.size(); i++) {
            assertNotNull(kotiki.get(i));
            assertEquals(kotiki.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testFindAllFriends() {
        List<Friends> friendsList = List.of(friends1, friends4);
        List<Kotik> expected = List.of(kotik3, kotik4);

        Mockito.when(friendsRepository.findAllByKotikId(1L)).thenReturn(friendsList);
        Mockito.when(kotikRepository.findById(3L)).thenReturn(Optional.of(kotik3));
        Mockito.when(kotikRepository.findById(4L)).thenReturn(Optional.of(kotik4));

        List<KotikDto> friends = kotikService.findFriends(1L);

        assertEquals(friends.size(), expected.size());
        for (int i = 0; i < friends.size(); i++) {
            assertNotNull(friends.get(i));
            assertEquals(friends.get(i).getId(), expected.get(i).getId());
        }
    }
}
