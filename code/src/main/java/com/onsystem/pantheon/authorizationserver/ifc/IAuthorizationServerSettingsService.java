package com.onsystem.pantheon.authorizationserver.ifc;


import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

public interface IAuthorizationServerSettingsService {

    AuthorizationServerSettings getAuthorizationServerSettings();

}
