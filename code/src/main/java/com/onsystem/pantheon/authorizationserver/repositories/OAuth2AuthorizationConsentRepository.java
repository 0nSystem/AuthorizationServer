package com.onsystem.pantheon.authorizationserver.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface OAuth2AuthorizationConsentRepository extends JpaRepository<OAuth2AuthorizationConsent, OAuth2AuthorizationConsentId> {


}
