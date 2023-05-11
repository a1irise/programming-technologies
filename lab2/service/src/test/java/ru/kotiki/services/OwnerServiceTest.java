package ru.kotiki.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.kotiki.dao.KotikDao;
import ru.kotiki.dao.OwnerDao;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Friends;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OwnerServiceTest {

    @Mock
    OwnerDao ownerDao;
    @Mock
    KotikDao kotikDao;

    @InjectMocks
    OwnerServiceImpl ownerService;

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
        Mockito.when(ownerDao.findById(1L)).thenReturn(owner1);

        Owner owner = ownerService.findById(1L);

        assertNotNull(owner);
        assertEquals(owner.getId(), owner1.getId());
    }

    @Test
    public void testFindAll() {
        List<Owner> expected = List.of(owner1, owner2, owner3);

        Mockito.when(ownerDao.findAll()).thenReturn(expected);

        List<Owner> owners = ownerService.findAll();

        assertEquals(owners.size(), expected.size());
        for (int i = 0; i < owners.size(); i++) {
            assertNotNull(owners.get(i));
            assertEquals(owners.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testSave() {
        List<Owner> expected = List.of(owner1);

        Mockito.when(ownerDao.findAll()).thenReturn(expected);

        ownerService.save(owner1);
        List<Owner> owners = ownerService.findAll();

        assertEquals(owners.size(), expected.size());
        for (int i = 0; i < owners.size(); i++) {
            assertNotNull(owners.get(i));
            assertEquals(owners.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testUpdate() {
        Owner owner = new Owner(
                "test",
                owner1.getDateOfBirth()
        );

        List<Owner> expected = List.of(owner);

        Mockito.when(ownerDao.findAll()).thenReturn(expected);

        owner1.setName("test");
        ownerService.update(owner1);
        List<Owner> owners = ownerService.findAll();

        assertEquals(owners.size(), expected.size());
        for (int i = 0; i < owners.size(); i++) {
            assertNotNull(owners.get(i));
            assertEquals(owners.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testDelete() {
        List<Owner> expected = List.of(owner1);

        Mockito.when(ownerDao.findAll()).thenReturn(expected);

        ownerService.save(owner1);
        ownerService.save(owner2);
        ownerService.delete(2L);

        List<Owner> owners = ownerService.findAll();

        assertEquals(owners.size(), expected.size());
        for (int i = 0; i < owners.size(); i++) {
            assertNotNull(owners.get(i));
            assertEquals(owners.get(i).getId(), expected.get(i).getId());
        }
    }

    @Test
    public void testFindKotiki() {
        List<Kotik> expected = List.of(kotik1, kotik2);

        Mockito.when(kotikDao.findByOwnerId(1L)).thenReturn(expected);

        List<Kotik> kotiki = ownerService.findKotiki(1L);

        assertEquals(kotiki.size(), expected.size());
        for (int i = 0; i < kotiki.size(); i++) {
            assertNotNull(kotiki.get(i));
            assertEquals(kotiki.get(i).getId(), expected.get(i).getId());
        }
    }
}
