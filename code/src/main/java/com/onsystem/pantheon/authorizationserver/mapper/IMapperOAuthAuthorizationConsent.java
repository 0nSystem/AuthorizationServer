package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsent;
import com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsentId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;

@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IMapperOAuthAuthorizationConsent {

    default OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent authorizationConsent) {
        final OAuth2AuthorizationConsent oauth2AuthorizationConsent = new OAuth2AuthorizationConsent();
        oauth2AuthorizationConsent.setId(
                OAuth2AuthorizationConsentId.builder()
                        .registeredClientId(UUID.fromString(authorizationConsent.getRegisteredClientId()))
                        .principalName(authorizationConsent.getPrincipalName())
                        .build()
        );

        return oauth2AuthorizationConsent;
    }

    //Scopes??
    default org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        return org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent
                .withId(String.valueOf(oAuth2AuthorizationConsent.getId().getRegisteredClientId()), oAuth2AuthorizationConsent.getId().getPrincipalName())
                .authorities(t -> t.addAll(oAuth2AuthorizationConsent.getAuthorities()))
                .build();
    }

}
