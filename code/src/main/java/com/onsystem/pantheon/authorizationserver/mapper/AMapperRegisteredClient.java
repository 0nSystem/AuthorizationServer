package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.UUID;


@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AMapperRegisteredClient {

    @Autowired
    private IMapperAuthenticationMethod iMapperAuthenticationMethod;
    @Autowired
    private IMapperAuthorizationGrandType iMapperAuthorizationGrandType;
    @Autowired
    private IMapperClientSettings iMapperClientSettings;
    @Autowired
    private IMapperScope iMapperScope;


    public RegisteredClient toRegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {
        return RegisteredClient.withId(String.valueOf(oauth2RegisteredClient.getId()))
                .clientId(oauth2RegisteredClient.getUser().getLogin())
                .clientName(oauth2RegisteredClient.getClientName())
                .clientIdIssuedAt(oauth2RegisteredClient.getClientIdIssuedAt())
                .clientSecret(oauth2RegisteredClient.getUser().getPassword())
                .clientSecretExpiresAt(null) //TODO
                .clientSettings(iMapperClientSettings.toClientSettings(oauth2RegisteredClient.getClientSettings())) //TODO Change model
                .clientAuthenticationMethods(l -> l.addAll(iMapperAuthenticationMethod.toClientAuthenticationMethods(oauth2RegisteredClient.getAuthorizationMethods())))
                .authorizationGrantTypes(l -> l.addAll(iMapperAuthorizationGrandType.toAuthorizationGrantTypes(oauth2RegisteredClient.getGrantTypes())))
                .scopes(l -> l.addAll(iMapperScope.toStr(oauth2RegisteredClient.getScopes())))
                .redirectUris(l -> l.addAll(oauth2RegisteredClient.getRedirectUris().stream().map(a -> a.getId().getUrl()).toList()))
                .build();
    }

    public Oauth2RegisteredClient toOauth2RegisteredClient(RegisteredClient registeredClient) {
        return Oauth2RegisteredClient.builder()
                .id(UUID.fromString(registeredClient.getId()))
                .clientName(registeredClient.getClientName())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .clientSettings(iMapperClientSettings.oauth2RegisteredClientAuthorizationClientSetting(registeredClient.getClientSettings())) //TODO change model
                .authorizationMethods(iMapperAuthenticationMethod.toOauth2AuthorizationMethods(registeredClient.getClientAuthenticationMethods()))
                .grantTypes(iMapperAuthorizationGrandType.toOauth2AuthorizationGrantType(registeredClient.getAuthorizationGrantTypes()))
                .scopes(iMapperScope.oauth2Scopes(registeredClient.getScopes()))
                .build();
    }


}


