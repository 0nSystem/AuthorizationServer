package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsentId;
import com.onsystem.pantheon.authorizationserver.mapper.IMapperOAuthAuthorizationConsent;
import com.onsystem.pantheon.authorizationserver.repositories.OAuth2AuthorizationConsentRepository;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Transactional
public class OAuth2AuthorizationConsentService implements org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService {


    private final OAuth2AuthorizationConsentRepository oAuth2AuthorizationConsentRepository;
    private final IMapperOAuthAuthorizationConsent mapperOAuthAuthorizationConsent;

    public OAuth2AuthorizationConsentService(OAuth2AuthorizationConsentRepository oAuth2AuthorizationConsentRepository, IMapperOAuthAuthorizationConsent mapperOAuthAuthorizationConsent) {
        this.oAuth2AuthorizationConsentRepository = oAuth2AuthorizationConsentRepository;
        this.mapperOAuthAuthorizationConsent = mapperOAuthAuthorizationConsent;
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent must not be null");
        final com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsent consent = mapperOAuthAuthorizationConsent.toOAuth2AuthorizationConsent(authorizationConsent);
        oAuth2AuthorizationConsentRepository.save(consent);
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        final com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsent consent = mapperOAuthAuthorizationConsent.toOAuth2AuthorizationConsent(authorizationConsent);
        oAuth2AuthorizationConsentRepository.delete(consent);
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        Assert.isNull(registeredClientId, "registeredClientId must not be null");
        Assert.isNull(principalName, "principalName must not be null");

        final OAuth2AuthorizationConsentId consentId = OAuth2AuthorizationConsentId
                .builder()
                .registeredClientId(UUID.fromString(registeredClientId))
                .principalName(principalName)
                .build();
        return oAuth2AuthorizationConsentRepository.findById(consentId)
                .map(mapperOAuthAuthorizationConsent::toOAuth2AuthorizationConsent)
                .orElse(null);
    }
}
