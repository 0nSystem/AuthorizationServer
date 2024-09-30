package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.OAuth2AuthorizationConsentEntity;
import com.onsystem.pantheon.authorizationserver.entities.Oauth2AuthorizationConsentId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
public interface IMapperAuthorizationConsent {

    /*
        TODO Scopes
     */

    default OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(OAuth2AuthorizationConsentEntity authorizationConsent) {
        return OAuth2AuthorizationConsent.withId(authorizationConsent.getId().getRegisteredClientId().toString(), authorizationConsent.getId().getPrincipalName())
                .authorities(l -> l.addAll(authorizationConsent.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())))
                .build();
    }


    default OAuth2AuthorizationConsentEntity toEntity(OAuth2AuthorizationConsent authorizationConsent) {
        final Oauth2AuthorizationConsentId id = new Oauth2AuthorizationConsentId();
        id.setPrincipalName(authorizationConsent.getPrincipalName());
        id.setRegisteredClientId(UUID.fromString(authorizationConsent.getRegisteredClientId()));

        final OAuth2AuthorizationConsentEntity oauth2AuthorizationConsent = new OAuth2AuthorizationConsentEntity();
        oauth2AuthorizationConsent.setId(id);
        oauth2AuthorizationConsent.setAuthorities(oauth2AuthorizationConsent.getAuthorities());

        return oauth2AuthorizationConsent;
    }
}
