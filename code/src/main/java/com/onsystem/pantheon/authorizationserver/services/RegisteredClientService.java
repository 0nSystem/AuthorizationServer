package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClientEntity;
import com.onsystem.pantheon.authorizationserver.mapper.IMapperRegisteredClient;
import com.onsystem.pantheon.authorizationserver.repositories.Oauth2RegisteredRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public class RegisteredClientService implements RegisteredClientRepository {

    private Oauth2RegisteredRepository oauth2RegisteredRepository;

    private IMapperRegisteredClient iMapperRegisteredClient;

    public RegisteredClientService(Oauth2RegisteredRepository oauth2RegisteredRepository, IMapperRegisteredClient iMapperRegisteredClient) {
        this.oauth2RegisteredRepository = oauth2RegisteredRepository;
        this.iMapperRegisteredClient = iMapperRegisteredClient;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        final Oauth2RegisteredClientEntity oauth2RegisteredClientEntity = iMapperRegisteredClient.toOauth2RegisteredClient(registeredClient);
        oauth2RegisteredRepository.save(oauth2RegisteredClientEntity);
    }

    @Override
    public RegisteredClient findById(String id) {
        return oauth2RegisteredRepository.findById(UUID.fromString(id))
                .map(iMapperRegisteredClient::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        //Review in starting clientId is a user login and now use in grant-type 'password'
        return oauth2RegisteredRepository.findByClientId(clientId)
                .map(iMapperRegisteredClient::toRegisteredClient)
                .orElse(null);
    }


}
