package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IMapperRegisteredClient {

    default RegisteredClient toRegisteredClient(Oauth2RegisteredClientEntity oauth2RegisteredClientEntity) {
        return RegisteredClient.withId(oauth2RegisteredClientEntity.getId().toString())
                .clientId(oauth2RegisteredClientEntity.getClientId())
                .clientSecret(oauth2RegisteredClientEntity.getClientSecret())
                .clientName(oauth2RegisteredClientEntity.getClientName())
                .clientIdIssuedAt(oauth2RegisteredClientEntity.getClientIdIssuedAt())
                .clientSecretExpiresAt(oauth2RegisteredClientEntity.getClientSecretExpiresAt())
                .clientSettings(ClientSettings.withSettings(oauth2RegisteredClientEntity.getClientSettings()).build())
                .clientAuthenticationMethods(l -> l.addAll(oauth2RegisteredClientEntity.getClientAuthenticationMethods().stream().map(ClientAuthenticationMethod::new).toList()))
                .authorizationGrantTypes(l -> l.addAll(oauth2RegisteredClientEntity.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::new).toList()))
                .scopes(l -> l.addAll(oauth2RegisteredClientEntity.getScopes()))
                .redirectUris(l -> l.addAll(oauth2RegisteredClientEntity.getRedirectUris()))
                .build();
    }

    default Oauth2RegisteredClientEntity toOauth2RegisteredClient(RegisteredClient registeredClient) {
        final UUID idRegisteredClient = UUID.fromString(registeredClient.getId());

        final Oauth2RegisteredClientEntity oauth2RegisteredClientMapped = new Oauth2RegisteredClientEntity();
        oauth2RegisteredClientMapped.setId(idRegisteredClient);
        oauth2RegisteredClientMapped.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        oauth2RegisteredClientMapped.setClientName(registeredClient.getClientName());
        oauth2RegisteredClientMapped.setClientSecret(registeredClient.getClientSecret());
        oauth2RegisteredClientMapped.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());

        oauth2RegisteredClientMapped.setClientSettings(registeredClient.getClientSettings().getSettings());
        oauth2RegisteredClientMapped.setTokenSettings(registeredClient.getTokenSettings().getSettings());
        oauth2RegisteredClientMapped.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet()));
        oauth2RegisteredClientMapped.setScopes(registeredClient.getScopes());
        oauth2RegisteredClientMapped.setAuthorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet()));
        oauth2RegisteredClientMapped.setRedirectUris(registeredClient.getRedirectUris());
        oauth2RegisteredClientMapped.setPostLogoutRedirectUris(registeredClient.getPostLogoutRedirectUris());

        return oauth2RegisteredClientMapped;
    }
}
