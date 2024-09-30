package com.onsystem.pantheon.authorizationserver.repositories;


import com.onsystem.pantheon.authorizationserver.entities.Oauth2AuthorizationEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface OAuth2AuthorizationRepository extends JpaRepository<Oauth2AuthorizationEntity, UUID>, JpaSpecificationExecutor<Oauth2AuthorizationEntity> {


}
