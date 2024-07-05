package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClient;
import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClientAuthorizationClientSetting;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AMapperClientSettings {


    public ClientSettings toClientSettings(Oauth2RegisteredClientAuthorizationClientSetting oauth2RegisteredClientAuthorizationClientSetting) {
        ClientSettings.Builder builder = ClientSettings.builder()
                .requireAuthorizationConsent(oauth2RegisteredClientAuthorizationClientSetting.getRequireAuthorizationConsent());
        if (StringUtils.hasText(oauth2RegisteredClientAuthorizationClientSetting.getJwtSetUrl())) {
            builder.jwkSetUrl(oauth2RegisteredClientAuthorizationClientSetting.getJwtSetUrl());
        }
        builder.requireProofKey(oauth2RegisteredClientAuthorizationClientSetting.getRequireProofKey());
        builder.tokenEndpointAuthenticationSigningAlgorithm(oauth2RegisteredClientAuthorizationClientSetting.getTokenEndpointAuthenticationSigningAlgorithm());
        return builder.build();
    }


    public Oauth2RegisteredClientAuthorizationClientSetting oauth2RegisteredClientAuthorizationClientSetting(ClientSettings clientSettings, final UUID registeredClientUUID) {
        return Oauth2RegisteredClientAuthorizationClientSetting.builder()
                .registeredClient(Oauth2RegisteredClient.builder().id(registeredClientUUID).build())
                .requireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent())
                .requireProofKey(clientSettings.isRequireProofKey())
                .jwtSetUrl(clientSettings.getJwkSetUrl())
                .tokenEndpointAuthenticationSigningAlgorithm((SignatureAlgorithm) clientSettings.getTokenEndpointAuthenticationSigningAlgorithm())
                .build();
    }
}
