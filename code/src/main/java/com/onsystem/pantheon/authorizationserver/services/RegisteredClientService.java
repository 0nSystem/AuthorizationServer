package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2RegisteredClient;
import com.onsystem.pantheon.authorizationserver.mapper.AMapperRegisteredClient;
import com.onsystem.pantheon.authorizationserver.repositories.Oauth2RegisteredRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RegisteredClientService implements RegisteredClientRepository {

    private Oauth2RegisteredRepository oauth2RegisteredRepository;

    private AMapperRegisteredClient aMapperRegisteredClient;

    public RegisteredClientService(Oauth2RegisteredRepository oauth2RegisteredRepository, AMapperRegisteredClient aMapperRegisteredClient) {
        this.oauth2RegisteredRepository = oauth2RegisteredRepository;
        this.aMapperRegisteredClient = aMapperRegisteredClient;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        final Oauth2RegisteredClient oauth2RegisteredClient = aMapperRegisteredClient.toOauth2RegisteredClient(registeredClient);
        oauth2RegisteredRepository.save(oauth2RegisteredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return oauth2RegisteredRepository.findById(Integer.valueOf(id))
                .map(aMapperRegisteredClient::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return oauth2RegisteredRepository.findByClientId(clientId)
                .map(aMapperRegisteredClient::toRegisteredClient)
                .orElse(null);
    }


}
