package ru.kotiki.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoSession;
import ru.kotiki.dao.KotikRepository;
import ru.kotiki.dao.OwnerRepository;
import ru.kotiki.dto.Mapper;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Friends;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OwnerServiceTest {

    @Mock
    KotikRepository kotikRepository;
    @Mock
    OwnerRepository ownerRepository;
    Mapper mapper = new Mapper();

    OwnerService ownerService;
    MockitoSession session;

    @BeforeEach
    public void beforeEach() {
        session = Mockito.mockitoSession()
                .initMocks(this)
                .startMocking();
        ownerService = new OwnerServiceImpl(ownerRepository, kotikRepository, mapper);
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
        Mockito.when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner1));

        OwnerDto owner = ownerService.findById(1L);

        assertNotNull(owner);
        assertEquals(owner.getId(), owner.getId());
        assertEquals(owner.getName(), owner.getName());
        assertEquals(owner.getDateOfBirth(), owner.getDateOfBirth());
    }

    @Test
    public void testFindAll() {
        List<Owner> expected = List.of(owner1, owner2, owner3);

        Mockito.when(ownerRepository.findAll()).thenReturn(expected);

        List<OwnerDto> owners = ownerService.findAll();
        assertEquals(owners.size(), expected.size());
        for (int i = 0; i < owners.size(); i++) {
            assertNotNull(owners.get(i));
            assertEquals(owners.get(i).getId(), expected.get(i).getId());
        }
    }
}
