package ru.kotiki.dao;

import org.springframework.data.jpa.repository.Query;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Kotik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KotikRepository extends JpaRepository<Kotik, Long> {

    @Query("FROM Kotik WHERE ownerId=:ownerId")
    List<Kotik> findByOwnerId(long ownerId);

    @Query("FROM Kotik WHERE color=:color")
    List<Kotik> findByColor(Color color);
}
