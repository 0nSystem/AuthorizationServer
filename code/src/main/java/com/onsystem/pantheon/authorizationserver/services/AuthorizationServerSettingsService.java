package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.entities.AuthorizationServerSettings;
import com.onsystem.pantheon.authorizationserver.ifc.IAuthorizationServerSettingsService;
import com.onsystem.pantheon.authorizationserver.repositories.AuthorizationSettingsRepository;
import jakarta.validation.constraints.NotNull;


public class AuthorizationServerSettingsService implements IAuthorizationServerSettingsService {

    private final Integer configurationAuthorizationSettingsId;
    private final AuthorizationSettingsRepository authorizationSettingsRepository;

    public AuthorizationServerSettingsService(Integer configurationAuthorizationSettingsId, AuthorizationSettingsRepository authorizationSettingsRepository) {
        this.configurationAuthorizationSettingsId = configurationAuthorizationSettingsId;
        this.authorizationSettingsRepository = authorizationSettingsRepository;
    }

    @Override
    public org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings getAuthorizationServerSettings() {
        final AuthorizationServerSettings authorizationServerSettings = authorizationSettingsRepository.findById(configurationAuthorizationSettingsId)
                .orElseThrow();
        return map(authorizationServerSettings);
    }

    private @NotNull org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings map(final @NotNull AuthorizationServerSettings authorizationServerSettings) {
        return org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings.builder()
                //.issuer(authorizationServerSetting.getIssuer())
                .authorizationEndpoint(authorizationServerSettings.getAuthorizationEndpoint())
                .deviceAuthorizationEndpoint(authorizationServerSettings.getDeviceAuthorizationEndpoint())
                .deviceVerificationEndpoint(authorizationServerSettings.getDeviceVerificationEndpoint())
                .tokenEndpoint(authorizationServerSettings.getTokenEndpoint())
                .jwkSetEndpoint(authorizationServerSettings.getJwkSetEndpoint())
                .tokenRevocationEndpoint(authorizationServerSettings.getTokenRevocationEndpoint())
                .tokenIntrospectionEndpoint(authorizationServerSettings.getTokenIntrospectionEndpoint())
                .oidcClientRegistrationEndpoint(authorizationServerSettings.getOidcClientRegistrationEndpoint())
                .oidcUserInfoEndpoint(authorizationServerSettings.getOidcUserInfoEndpoint())
                .oidcLogoutEndpoint(authorizationServerSettings.getOidcLogoutEndpoint())
                .build();

    }

}
