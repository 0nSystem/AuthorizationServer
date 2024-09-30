package com.onsystem.pantheon.authorizationserver.repositories;

import com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsentEntity;
import com.onsystem.pantheon.authorizationserver.entities.Oauth2AuthorizationConsentId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface OAuth2AuthorizationConsentRepository extends JpaRepository<OAuth2AuthorizationConsentEntity, Oauth2AuthorizationConsentId> {


}
