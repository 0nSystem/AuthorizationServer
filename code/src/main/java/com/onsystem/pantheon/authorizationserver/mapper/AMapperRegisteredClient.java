package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClient;
import com.onsystem.pantheon.authorizationserver.repositories.Oauth2RegisteredRepository;
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
    private AMapperClientSettings aMapperClientSettings;
    @Autowired
    private IMapperScope iMapperScope;

    @Autowired
    private Oauth2RegisteredRepository oauth2RegisteredRepository;


    public RegisteredClient toRegisteredClient(Oauth2RegisteredClient oauth2RegisteredClient) {
        return RegisteredClient.withId(String.valueOf(oauth2RegisteredClient.getId()))
                .clientId(oauth2RegisteredClient.getUser().getLogin())
                .clientSecret(oauth2RegisteredClient.getUser().getPassword())
                .clientName(oauth2RegisteredClient.getClientName())
                .clientIdIssuedAt(oauth2RegisteredClient.getClientIdIssuedAt())
                .clientSecretExpiresAt(null) //TODO
                .clientSettings(aMapperClientSettings.toClientSettings(oauth2RegisteredClient.getClientSettings()))
                .clientAuthenticationMethods(l -> l.addAll(iMapperAuthenticationMethod.toClientAuthenticationMethods(oauth2RegisteredClient.getAuthorizationMethods())))
                .authorizationGrantTypes(l -> l.addAll(iMapperAuthorizationGrandType.toAuthorizationGrantTypes(oauth2RegisteredClient.getGrantTypes())))
                .scopes(l -> l.addAll(iMapperScope.toStr(oauth2RegisteredClient.getScopes())))
                .redirectUris(l -> l.addAll(oauth2RegisteredClient.getRedirectUris().stream().map(a -> a.getId().getUrl()).toList()))
                .build();
    }

    public Oauth2RegisteredClient toOauth2RegisteredClient(RegisteredClient registeredClient) {
        final UUID idRegisteredClient = UUID.fromString(registeredClient.getId());

        final Oauth2RegisteredClient oauth2RegisteredClientMapped = Oauth2RegisteredClient.builder()
                .id(idRegisteredClient)
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .build();

        final Oauth2RegisteredClient registeredClientBBDD = oauth2RegisteredRepository.findById(idRegisteredClient)
                .orElseThrow();
        oauth2RegisteredClientMapped.setClientName(registeredClientBBDD.getClientName());
        oauth2RegisteredClientMapped.setApplication(registeredClientBBDD.getApplication());
        oauth2RegisteredClientMapped.setUser(registeredClientBBDD.getUser());
        oauth2RegisteredClientMapped.setClientSettings(registeredClientBBDD.getClientSettings());
        oauth2RegisteredClientMapped.setTokenSettings(registeredClientBBDD.getTokenSettings());
        oauth2RegisteredClientMapped.setGrantTypes(registeredClientBBDD.getGrantTypes());
        oauth2RegisteredClientMapped.setScopes(registeredClientBBDD.getScopes());
        oauth2RegisteredClientMapped.setAuthorizationMethods(registeredClientBBDD.getAuthorizationMethods());
        oauth2RegisteredClientMapped.setRedirectUris(registeredClientBBDD.getRedirectUris());
        oauth2RegisteredClientMapped.setLogoutRedirectUris(registeredClientBBDD.getLogoutRedirectUris());

        return oauth2RegisteredClientMapped;
    }


}


