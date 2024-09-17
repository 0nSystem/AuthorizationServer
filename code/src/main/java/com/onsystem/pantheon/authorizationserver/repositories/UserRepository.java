package com.onsystem.pantheon.authorizationserver.repositories;

import com.onsystem.pantheon.authorizationserver.entities.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByLogin(String username);
}
