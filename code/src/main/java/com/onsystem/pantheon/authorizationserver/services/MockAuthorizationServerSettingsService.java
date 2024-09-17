package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.ifc.IAuthorizationServerSettingsService;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

public class MockAuthorizationServerSettingsService implements IAuthorizationServerSettingsService {
    @Override
    public AuthorizationServerSettings getAuthorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
}
