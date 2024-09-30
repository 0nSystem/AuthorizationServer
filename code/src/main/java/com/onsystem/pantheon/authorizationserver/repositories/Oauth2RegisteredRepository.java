package com.onsystem.pantheon.authorizationserver.repositories;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface Oauth2RegisteredRepository extends JpaRepository<Oauth2RegisteredClient, UUID> {

    @Query("select rc from Oauth2RegisteredClient rc where rc.user.login = :clientId")
    Optional<Oauth2RegisteredClient> findByClientId(@Param("clientId") String clientId);

}
