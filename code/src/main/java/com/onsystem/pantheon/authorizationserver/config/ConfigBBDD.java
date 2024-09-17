package com.onsystem.pantheon.authorizationserver.config;

import com.onsystem.pantheon.authorizationserver.ifc.IAuthorizationServerSettingsService;
import com.onsystem.pantheon.authorizationserver.mapper.AMapperOAuth2Authorization;
import com.onsystem.pantheon.authorizationserver.mapper.AMapperRegisteredClient;
import com.onsystem.pantheon.authorizationserver.mapper.IMapperOAuthAuthorizationConsent;
import com.onsystem.pantheon.authorizationserver.mapper.IMapperUser;
import com.onsystem.pantheon.authorizationserver.repositories.*;
import com.onsystem.pantheon.authorizationserver.services.AuthorizationServerSettingsService;
import com.onsystem.pantheon.authorizationserver.services.OAuth2AuthorizationServiceImpl;
import com.onsystem.pantheon.authorizationserver.services.RegisteredClientService;
import com.onsystem.pantheon.authorizationserver.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public class ConfigBBDD {

    @Value("${auth.configurationSettingsId}")
    private Integer configurationAuthorizationSettingsId;

    @Bean
    public IAuthorizationServerSettingsService authorizationServerSettingsService(AuthorizationSettingsRepository authorizationSettingsRepository) {
        return new AuthorizationServerSettingsService(configurationAuthorizationSettingsId, authorizationSettingsRepository);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(
            Oauth2RegisteredRepository oauth2RegisteredRepository,
            AMapperRegisteredClient aMapperRegisteredClient
    ) {
        return new RegisteredClientService(oauth2RegisteredRepository, aMapperRegisteredClient);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository, IMapperUser iMapperUser) {
        return new UserDetailsServiceImpl(userRepository, iMapperUser);
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(OAuth2AuthorizationRepository oAuth2AuthorizationRepository, AMapperOAuth2Authorization aMapperOAuth2Authorization) {
        return new OAuth2AuthorizationServiceImpl(oAuth2AuthorizationRepository, aMapperOAuth2Authorization);
    }


    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(OAuth2AuthorizationConsentRepository oAuth2AuthorizationConsentRepository, IMapperOAuthAuthorizationConsent iMapperOAuthAuthorizationConsent) {
        return new com.onsystem.pantheon.authorizationserver.services.OAuth2AuthorizationConsentService(
                oAuth2AuthorizationConsentRepository, iMapperOAuthAuthorizationConsent
        );
    }


}