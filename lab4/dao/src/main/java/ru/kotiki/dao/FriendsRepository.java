package ru.kotiki.dao;

import ru.kotiki.entities.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query("FROM Friends WHERE (kotik1Id=:kotik1Id and kotik2Id=:kotik2Id) or (kotik1Id=:kotik2Id and kotik2Id=:kotik1Id)")
    Friends findByKotikiId(long kotik1Id, long kotik2Id);

    @Query("FROM Friends WHERE kotik1Id=:kotikId or kotik2Id=:kotikId")
    List<Friends> findAllByKotikId(long kotikId);
}
