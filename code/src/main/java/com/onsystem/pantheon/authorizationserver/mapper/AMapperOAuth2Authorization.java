package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.OAuth2Authorization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AMapperOAuth2Authorization {


    public OAuth2Authorization toOAuth2Authorization(
            org.springframework.security.oauth2.server.authorization.OAuth2Authorization authorization
    ) {
        final OAuth2Authorization oAuth2Authorization = new OAuth2Authorization();
        oAuth2Authorization.setId(UUID.fromString(authorization.getId()));
        oAuth2Authorization.setRegisteredClientId(UUID.fromString(authorization.getRegisteredClientId()));
        oAuth2Authorization.setPrincipalName(authorization.getPrincipalName());
        oAuth2Authorization.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        oAuth2Authorization.setAuthorizedScopes(oAuth2Authorization.getAuthorizedScopes());
        oAuth2Authorization.setAttributes(authorization.getAttributes());

        String state = null;
        String authorizationState = authorization.getAttribute(OAuth2ParameterNames.STATE);
        if (StringUtils.hasText(authorizationState)) {
            state = authorizationState;
        }
        oAuth2Authorization.setState(state);

        //Authorization code
        org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                authorization.getToken(OAuth2AuthorizationCode.class);
        if (authorizationCode != null) {
            Optional.ofNullable(authorizationCode.getToken().getIssuedAt()).ifPresent(oAuth2Authorization::setAuthorizationCodeIssuedAt);
            Optional.ofNullable(authorizationCode.getToken().getExpiresAt()).ifPresent(oAuth2Authorization::setAuthorizationCodeExpiresAt);
            oAuth2Authorization.setAuthorizationCodeValue(authorizationCode.getToken().getTokenValue());
            oAuth2Authorization.setAuthorizationCodeMetadata(authorizationCode.getMetadata());
        }

        org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<OAuth2AccessToken> accessToken =
                authorization.getToken(OAuth2AccessToken.class);
        if (accessToken != null) {
            Optional.ofNullable(accessToken.getToken().getIssuedAt()).ifPresent(oAuth2Authorization::setAccessTokenIssuedAt);
            Optional.ofNullable(accessToken.getToken().getExpiresAt()).ifPresent(oAuth2Authorization::setAccessTokenExpiresAt);
            oAuth2Authorization.setAccessTokenValue(accessToken.getToken().getTokenValue());
            oAuth2Authorization.setAccessTokenMetadata(accessToken.getMetadata());
            oAuth2Authorization.setAccessTokenType(accessToken.getToken().getTokenType());
            if (!CollectionUtils.isEmpty(accessToken.getToken().getScopes())) {
                oAuth2Authorization.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
            }
        }

        org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<OidcIdToken> oidcIdToken =
                authorization.getToken(OidcIdToken.class);
        if (oidcIdToken != null) {
            Optional.ofNullable(oidcIdToken.getToken().getIssuedAt()).ifPresent(oAuth2Authorization::setOidcIdTokenIssuedAt);
            Optional.ofNullable(oidcIdToken.getToken().getExpiresAt()).ifPresent(oAuth2Authorization::setOidcIdTokenExpiresAt);
            oAuth2Authorization.setOidcIdTokenValue(oidcIdToken.getToken().getTokenValue());
            oAuth2Authorization.setOidcIdTokenMetadata(oidcIdToken.getMetadata());
        }

        org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();
        if (refreshToken != null) {
            Optional.ofNullable(refreshToken.getToken().getIssuedAt()).ifPresent(oAuth2Authorization::setRefreshTokenIssuedAt);
            Optional.ofNullable(refreshToken.getToken().getExpiresAt()).ifPresent(oAuth2Authorization::setRefreshTokenExpiresAt);
            oAuth2Authorization.setRefreshTokenValue(refreshToken.getToken().getTokenValue());
            oAuth2Authorization.setRefreshTokenMetadata(refreshToken.getMetadata());
        }

        org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<OAuth2UserCode> userCode = authorization.getToken(OAuth2UserCode.class);
        if (userCode != null) {
            Optional.ofNullable(userCode.getToken().getIssuedAt()).ifPresent(oAuth2Authorization::setUserCodeIssuedAt);
            Optional.ofNullable(userCode.getToken().getExpiresAt()).ifPresent(oAuth2Authorization::setUserCodeExpiresAt);
            oAuth2Authorization.setUserCodeValue(userCode.getToken().getTokenValue());
            oAuth2Authorization.setUserCodeMetadata(userCode.getMetadata());
        }

        org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = authorization.getToken(OAuth2DeviceCode.class);
        if (deviceCode != null) {
            Optional.ofNullable(deviceCode.getToken().getIssuedAt()).ifPresent(oAuth2Authorization::setDeviceCodeIssuedAt);
            Optional.ofNullable(deviceCode.getToken().getExpiresAt()).ifPresent(oAuth2Authorization::setDeviceCodeExpiresAt);
            oAuth2Authorization.setDeviceCodeValue(deviceCode.getToken().getTokenValue());
            oAuth2Authorization.setDeviceCodeMetadata(deviceCode.getMetadata());
        }

        return oAuth2Authorization;
    }

    public org.springframework.security.oauth2.server.authorization.OAuth2Authorization toOAuth2Authorization(OAuth2Authorization oAuth2Authorization) {

        //Oauth2Authorization require entity RegisteredClient to make
        final RegisteredClient registeredClient = RegisteredClient.withId(String.valueOf(oAuth2Authorization.getRegisteredClientId())) //TODO review
                .build();

        final org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Builder authorizationBuilder = org.springframework.security.oauth2.server.authorization.OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(String.valueOf(oAuth2Authorization.getId()))
                .principalName(oAuth2Authorization.getPrincipalName())
                .authorizationGrantType(new AuthorizationGrantType(oAuth2Authorization.getAuthorizationGrantType()))
                .authorizedScopes(oAuth2Authorization.getAuthorizedScopes())
                .attributes(map -> map.putAll(oAuth2Authorization.getAttributes()));

        if (StringUtils.hasText(oAuth2Authorization.getAuthorizationCodeValue())) {
            final OAuth2AuthorizationCode oAuth2AuthorizationCode = new OAuth2AuthorizationCode(
                    oAuth2Authorization.getAuthorizationCodeValue(),
                    oAuth2Authorization.getAuthorizationCodeIssuedAt(),
                    oAuth2Authorization.getAuthorizationCodeExpiresAt());
            authorizationBuilder.token(oAuth2AuthorizationCode, (metadata) -> metadata.putAll(oAuth2Authorization.getAuthorizationCodeMetadata()));
        }

        if (StringUtils.hasText(oAuth2Authorization.getAccessTokenValue())) {
            final OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(
                    oAuth2Authorization.getAccessTokenType(),
                    oAuth2Authorization.getAccessTokenValue(),
                    oAuth2Authorization.getAccessTokenIssuedAt(),
                    oAuth2Authorization.getAccessTokenExpiresAt(),
                    oAuth2Authorization.getAuthorizedScopes()
            );
            authorizationBuilder.token(oAuth2AccessToken, (metadata) -> metadata.putAll(oAuth2Authorization.getAccessTokenMetadata()));
        }

        if (StringUtils.hasText(oAuth2Authorization.getOidcIdTokenValue())) {
            final OidcIdToken oidcIdToken = new OidcIdToken(
                    oAuth2Authorization.getOidcIdTokenValue(),
                    oAuth2Authorization.getOidcIdTokenIssuedAt(),
                    oAuth2Authorization.getOidcIdTokenExpiresAt(),
                    (Map<String, Object>) oAuth2Authorization.getOidcIdTokenMetadata().get(org.springframework.security.oauth2.server.authorization.OAuth2Authorization.Token.CLAIMS_METADATA_NAME)
            );
            authorizationBuilder.token(oidcIdToken, (metadata) -> metadata.putAll(oAuth2Authorization.getOidcIdTokenMetadata()));
        }

        if (StringUtils.hasText(oAuth2Authorization.getRefreshTokenValue())) {

            final OAuth2RefreshToken oAuth2RefreshToken = new OAuth2RefreshToken(
                    oAuth2Authorization.getRefreshTokenValue(),
                    oAuth2Authorization.getRefreshTokenIssuedAt(),
                    oAuth2Authorization.getRefreshTokenExpiresAt()
            );
            authorizationBuilder.token(oAuth2RefreshToken, (metadata) -> metadata.putAll(oAuth2Authorization.getRefreshTokenMetadata()));
        }

        if (StringUtils.hasText(oAuth2Authorization.getUserCodeValue())) {
            final OAuth2UserCode oAuth2UserCode = new OAuth2UserCode(
                    oAuth2Authorization.getUserCodeValue(),
                    oAuth2Authorization.getUserCodeIssuedAt(),
                    oAuth2Authorization.getUserCodeExpiresAt()
            );
            authorizationBuilder.token(oAuth2UserCode, (metadata) -> metadata.putAll(oAuth2Authorization.getUserCodeMetadata()));
        }

        if (StringUtils.hasText(oAuth2Authorization.getDeviceCodeValue())) {
            final OAuth2DeviceCode oAuth2DeviceCode = new OAuth2DeviceCode(
                    oAuth2Authorization.getDeviceCodeValue(),
                    oAuth2Authorization.getDeviceCodeIssuedAt(),
                    oAuth2Authorization.getDeviceCodeExpiresAt()
            );
            authorizationBuilder.token(oAuth2DeviceCode, (metadata) -> metadata.putAll(oAuth2Authorization.getDeviceCodeMetadata()));
        }

        return authorizationBuilder.build();
    }


}
