package com.onsystem.pantheon.authorizationserver.mappers;


import com.onsystem.pantheon.authorizationserver.entities.Oauth2AuthorizationEntity;
import com.onsystem.pantheon.authorizationserver.mapper.IMapperAuthorization;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class MapperAuthorizationTest {

    @Autowired
    private IMapperAuthorization iMapperAuthorization;


    @Test
    public void whenMapperAuthorizationEntityToAuthorization() {
        final Oauth2AuthorizationEntity entity = new Oauth2AuthorizationEntity();
        entity.setId(UUID.randomUUID());
        entity.setRegisteredClientId(UUID.randomUUID());

        entity.setPrincipalName("example_user");
        entity.setAuthorizationGrantType("authorization_code");

        entity.setAuthorizedScopes(new String[]{"read", "write"});
        entity.setAttributes(Map.of("ip", "192.168.0.1", "user_agent", "Mozilla/5.0"));

        entity.setAuthorizationCodeValue("auth_code_example");
        entity.setAuthorizationCodeIssuedAt(Instant.now());
        entity.setAuthorizationCodeExpiresAt(Instant.now().plusSeconds(600));
        entity.setAuthorizationCodeMetadata(Map.of("redirect_uri", "https://example.com/callback"));

        entity.setAccessTokenValue("access_token_example");
        entity.setAccessTokenIssuedAt(Instant.now());
        entity.setAccessTokenExpiresAt(Instant.now().plusSeconds(3600));
        entity.setAccessTokenMetadata(Map.of("token_type", "Bearer"));
        entity.setAccessTokenType(OAuth2AccessToken.TokenType.BEARER);
        entity.setAccessTokenScopes(Set.of("read", "write"));

        entity.setOidcIdTokenValue("oidc_id_token_example");
        entity.setOidcIdTokenIssuedAt(Instant.now());
        entity.setOidcIdTokenExpiresAt(Instant.now().plusSeconds(3600));
        entity.setOidcIdTokenMetadata(Map.of("issuer", "https://example.com"));

        entity.setRefreshTokenValue("refresh_token_example");
        entity.setRefreshTokenIssuedAt(Instant.now());
        entity.setRefreshTokenExpiresAt(Instant.now().plusSeconds(86400));
        entity.setRefreshTokenMetadata(Map.of("scope", "offline_access"));

        entity.setUserCodeValue("user_code_example");
        entity.setUserCodeIssuedAt(Instant.now());
        entity.setUserCodeExpiresAt(Instant.now().plusSeconds(300));
        entity.setUserCodeMetadata(Map.of("device_id", "12345"));

        entity.setDeviceCodeValue("device_code_example");
        entity.setDeviceCodeIssuedAt(Instant.now());
        entity.setDeviceCodeExpiresAt(Instant.now().plusSeconds(600));
        entity.setDeviceCodeMetadata(Map.of("device_ip", "192.168.0.100"));

        final OAuth2Authorization oauthMapped = iMapperAuthorization.toOAuth2Authorization(entity);
        validationMapperAuthorizationEntityToAuthorization(entity, oauthMapped);
    }

    public void validationMapperAuthorizationEntityToAuthorization(
            final Oauth2AuthorizationEntity entity, final OAuth2Authorization authorization
    ) {
        Assert.assertNotNull(entity);
        Assert.assertNotNull(authorization);

        assertEquals(entity.getId().toString(), authorization.getId());
        assertEquals(entity.getRegisteredClientId().toString(), authorization.getRegisteredClientId());
        assertEquals(entity.getPrincipalName(), authorization.getPrincipalName());

        assertEquals(entity.getAuthorizationGrantType(), authorization.getAuthorizationGrantType().getValue());

        //TODO controller is list if null?
        assertTrue(CollectionUtils.containsAny(Arrays.stream(entity.getAuthorizedScopes()).toList(), authorization.getAuthorizedScopes()));

        assertEquals(entity.getAttributes(), authorization.getAttributes());

        assertEquals(entity.getState(), authorization.getAttribute(OAuth2ParameterNames.STATE));

        final OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
        assertEquals(entity.getAuthorizationCodeValue(), authorizationCode.getToken().getTokenValue());
        assertEquals(entity.getAuthorizationCodeMetadata(), authorizationCode.getMetadata());
        assertEquals(entity.getAuthorizationCodeIssuedAt(), authorizationCode.getToken().getIssuedAt());
        assertEquals(entity.getAuthorizationCodeExpiresAt(), authorizationCode.getToken().getExpiresAt());

        final OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        assertEquals(entity.getAccessTokenValue(), accessToken.getToken().getTokenValue());
        assertEquals(entity.getAccessTokenType(), accessToken.getToken().getTokenType());
        assertEquals(entity.getAccessTokenMetadata(), accessToken.getMetadata());
        assertEquals(entity.getAccessTokenScopes(), accessToken.getToken().getScopes());
        assertEquals(entity.getAccessTokenIssuedAt(), accessToken.getToken().getIssuedAt());
        assertEquals(entity.getAuthorizationCodeExpiresAt(), accessToken.getToken().getExpiresAt());

        final OAuth2Authorization.Token<OidcIdToken> oidcIdToken = authorization.getToken(OidcIdToken.class);
        assertEquals(entity.getOidcIdTokenValue(), oidcIdToken.getToken().getTokenValue());
        assertEquals(entity.getOidcIdTokenMetadata(), oidcIdToken.getMetadata());
        assertEquals(entity.getOidcIdTokenIssuedAt(), oidcIdToken.getToken().getIssuedAt());
        assertEquals(entity.getOidcIdTokenExpiresAt(), oidcIdToken.getToken().getExpiresAt());

        final OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        assertEquals(entity.getRefreshTokenMetadata(), refreshToken.getMetadata());
        assertEquals(entity.getRefreshTokenValue(), refreshToken.getToken().getTokenValue());
        assertEquals(entity.getRefreshTokenExpiresAt(), refreshToken.getToken().getExpiresAt());
        assertEquals(entity.getRefreshTokenIssuedAt(), refreshToken.getToken().getIssuedAt());

        final OAuth2Authorization.Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);
        assertEquals(entity.getUserCodeValue(), userCode.getToken().getTokenValue());
        assertEquals(entity.getUserCodeMetadata(), userCode.getMetadata());
        assertEquals(entity.getUserCodeIssuedAt(), userCode.getToken().getIssuedAt());
        assertEquals(entity.getUserCodeExpiresAt(), userCode.getToken().getExpiresAt());

        final OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);
        assertEquals(entity.getDeviceCodeValue(), deviceCode.getToken().getTokenValue());
        assertEquals(entity.getDeviceCodeMetadata(), deviceCode.getMetadata());
        assertEquals(entity.getDeviceCodeIssuedAt(), deviceCode.getToken().getIssuedAt());
        assertEquals(entity.getDeviceCodeExpiresAt(), deviceCode.getToken().getExpiresAt());
    }

}
