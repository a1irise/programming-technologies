package ru.kotiki.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kotiki.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE username=:username")
    User findByUsername(String username);
}
