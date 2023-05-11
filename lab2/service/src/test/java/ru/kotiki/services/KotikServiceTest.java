package ru.kotiki.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.kotiki.dao.*;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Friends;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KotikServiceTest {

    @Mock
    KotikDao kotikDao;
    @Mock
    OwnerDao ownerDao;
    @Mock
    FriendsDao friendsDao;

    @InjectMocks
    KotikServiceImpl kotikService;

    MockitoSession session;

    @BeforeEach
    public void beforeEach() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
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
        owner2 = new Owner("Jason", LocalDate.of(1, 2, 3));
        owner2.setId(2L);
        owner3 = new Owner("Jessica", LocalDate.of(1, 2, 3));
        owner3.setId(3L);

        kotik1 = new Kotik("Milo", LocalDate.of(1, 2, 3), "stray", Color.BLACK, 1L);
        kotik1.setId(1L);
        kotik2 = new Kotik("Ralf", LocalDate.of(1, 2, 3), "british", Color.GINGER, 1L);
        kotik2.setId(2L);
        kotik3 = new Kotik("Bella", LocalDate.of(1, 2, 3), "scottish", Color.CREAM, 2L);
        kotik1.setId(3L);
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
        Mockito.when(kotikDao.findById(1L)).thenReturn(kotik1);

        Kotik kotik = kotikService.findById(1L);

        assertNotNull(kotik);
        assertEquals(kotik.getId(), kotik1.getId());
    }

    @Test
    public void testFindAll() {
        List<Kotik> expected = List.of(kotik1, kotik2, kotik3, kotik4);

        Mockito.when(kotikDao.findAll()).thenReturn(expected);

        List<Kotik> kotiki = kotikService.findAll();

        assertEquals(kotiki.size(), expected.size());
        for (int i = 0; i < kotiki.size(); i++) {
            assertNotNull(kotiki.get(i));
            assertEquals(kotiki.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testSave() {
        List<Kotik> expected = List.of(kotik1);

        Mockito.when(kotikDao.findAll()).thenReturn(expected);

        kotikService.save(kotik1);
        List<Kotik> kotiki = kotikService.findAll();

        assertEquals(kotiki.size(), expected.size());
        for (int i = 0; i < kotiki.size(); i++) {
            assertNotNull(kotiki.get(i));
            assertEquals(kotiki.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testUpdate() {
        Kotik kotik = new Kotik(
                "test",
                kotik1.getDateOfBirth(),
                kotik1.getBreed(),
                kotik1.getColor(),
                kotik1.getOwnerId()
        );

        List<Kotik> expected = List.of(kotik);

        Mockito.when(kotikDao.findAll()).thenReturn(expected);

        kotik1.setName("test");
        kotikService.update(kotik1);
        List<Kotik> kotiki = kotikService.findAll();

        assertEquals(kotiki.size(), expected.size());
        for (int i = 0; i < kotiki.size(); i++) {
            assertNotNull(kotiki.get(i));
            assertEquals(kotiki.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testDelete() {
        List<Kotik> expected = List.of(kotik1);

        Mockito.when(kotikDao.findAll()).thenReturn(expected);

        kotikService.save(kotik1);
        kotikService.save(kotik2);
        kotikService.delete(2L);

        List<Kotik> kotiki = kotikService.findAll();

        assertEquals(kotiki.size(), expected.size());
        for (int i = 0; i < kotiki.size(); i++) {
            assertNotNull(kotiki.get(i));
            assertEquals(kotiki.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testFindOwner() {
        Mockito.when(kotikDao.findById(1L)).thenReturn(kotik1);
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner1);

        Owner owner = kotikService.findOwner(1L);

        assertNotNull(owner);
        assertEquals(owner.getId(), owner1.getId());
    }

    @Test
    public void testFindFriends() {
        List<Friends> expectedFriends = List.of(friends1, friends4);
        List<Kotik> expected = List.of(kotik3, kotik4);

        Mockito.when(friendsDao.findByKotikId(1L)).thenReturn(expectedFriends);
        Mockito.when(kotikDao.findById(3L)).thenReturn(kotik3);
        Mockito.when(kotikDao.findById(4L)).thenReturn(kotik4);

        List<Kotik> friends = kotikService.findFriends(1L);

        assertEquals(friends.size(), expected.size());
        for (int i = 0; i < friends.size(); i++) {
            assertNotNull(friends.get(i));
            assertEquals(friends.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testIsFriends() {
        Mockito.when(friendsDao.findByKotikiId(1L, 3L)).thenReturn(friends1);

        boolean result = kotikService.isFriends(1L, 3L);

        assertTrue(result);
    }

    @Test
    public void testFriend() {
        List<Kotik> expected = List.of(kotik3);

        Mockito.when(friendsDao.findByKotikiId(1L, 3L)).thenReturn(null);
        Mockito.when(friendsDao.findByKotikId(1L)).thenReturn(List.of(friends1));
        Mockito.when(kotikDao.findById(3L)).thenReturn(kotik3);

        kotikService.friend(1, 3);

        List<Kotik> friends = kotikService.findFriends(1L);

        assertEquals(friends.size(), expected.size());
        for (int i = 0; i < friends.size(); i++) {
            assertNotNull(friends.get(i));
            assertEquals(friends.get(i).getId(), expected.get(i).getId());
        }

    }

    @Test
    public void testUnfriend() {
        List<Kotik> expected = List.of(kotik4);

        Mockito.when(friendsDao.findByKotikiId(1L, 3L)).thenReturn(friends1);
        Mockito.when(friendsDao.findByKotikId(1L)).thenReturn(List.of(friends4));
        Mockito.when(kotikDao.findById(4L)).thenReturn(kotik4);

        friendsDao.save(friends1);
        friendsDao.save(friends4);
        kotikService.unfriend(1L, 3L);

        List<Kotik> friends = kotikService.findFriends(1L);

        assertEquals(friends.size(), expected.size());
        for (int i = 0; i < friends.size(); i++) {
            assertNotNull(friends.get(i));
            assertEquals(friends.get(i).getId(), expected.get(i).getId());
        }
    }
}
