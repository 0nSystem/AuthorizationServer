package com.onsystem.pantheon.authorizationserver.repositories;


import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClientEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface Oauth2RegisteredRepository extends JpaRepository<Oauth2RegisteredClientEntity, UUID> {

    Optional<Oauth2RegisteredClientEntity> findByClientId(@Param("clientId") String clientId);

}
