package com.onsystem.pantheon.authorizationserver.integration.authorization;

import com.onsystem.pantheon.authorizationserver.entities.AuthorizationServerSettings;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenEndpointTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthorizationServerSettings authorizationServerSettings;


    @Test
    public void whenRequestTokenClientCredentialsIsCorrect() {
        final ResponseEntity<OAuth2AccessTokenResponse> response = createClientCredentialsTokenRequest(authorizationServerSettings.getTokenEndpoint());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        validationAccessToken(response.getBody());

    }

    @Test
    public void whenRequestTokenAuthorizationCodeIsCorrect() {
        final ResponseEntity<OAuth2AccessTokenResponse> response = createAuthorizationCodeTokenRequest(authorizationServerSettings.getTokenEndpoint());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    public void validationAccessToken(OAuth2AccessTokenResponse accessTokenResponse) {
        Assertions.assertNotNull(accessTokenResponse);
        Assertions.assertNotNull(accessTokenResponse.getAccessToken());
        Assertions.assertNotNull(accessTokenResponse.getAccessToken().getTokenValue());
        Assertions.assertEquals("Bearer", accessTokenResponse.getAccessToken().getTokenType().getValue());
        Assertions.assertNotNull(accessTokenResponse.getAccessToken().getExpiresAt());
    }

    private ResponseEntity<OAuth2AccessTokenResponse> createClientCredentialsTokenRequest(final String tokenEndpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
        body.add(OAuth2ParameterNames.USERNAME, "onsystem_name");
        body.add(OAuth2ParameterNames.PASSWORD, "password");
        body.add(OAuth2ParameterNames.CLIENT_ID, "srvauthorizationserver");
        body.add(OAuth2ParameterNames.CLIENT_SECRET, "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(tokenEndpoint, request, OAuth2AccessTokenResponse.class);
    }

    private ResponseEntity<OAuth2AccessTokenResponse> createAuthorizationCodeTokenRequest(final String tokenEndpoint) {
        String[] redirectUris = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        body.add(OAuth2ParameterNames.CODE, "code");
        //body.add(OAuth2ParameterNames.REDIRECT_URI, redirectUris[0]);
        body.add(OAuth2ParameterNames.CLIENT_ID, "srvauthorizationserver");
        body.add(OAuth2ParameterNames.CLIENT_SECRET, "password");


        return restTemplate.postForEntity(tokenEndpoint, body, OAuth2AccessTokenResponse.class);
    }

    public ResponseEntity<OAuth2AccessTokenResponse> createRefreshTokenTokenRequest(final String tokenEndpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.REFRESH_TOKEN.getValue());
        body.add(OAuth2ParameterNames.REFRESH_TOKEN, "refresh-token");
        //body.add(OAuth2ParameterNames.SCOPE, StringUtils.collectionToDelimitedString(registeredClient.getScopes(), " "));

        return restTemplate.postForEntity(tokenEndpoint, body, OAuth2AccessTokenResponse.class);
    }

    /*
    public ResponseEntity<OAuth2AccessTokenResponse> createTokenExchangeTokenRequest(final String tokenEndpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.REFRESH_TOKEN.getValue());
        body.add(OAuth2ParameterNames.REFRESH_TOKEN, "refresh-token");
        //body.add(OAuth2ParameterNames.SCOPE, StringUtils.collectionToDelimitedString(registeredClient.getScopes(), " "));

        body.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.TOKEN.getValue());
        body.add(OAuth2ParameterNames.S, "subject-token");
        body.add(OAuth2ParameterNames.SUBJECT_TOKEN_TYPE, ACCESS_TOKEN_TYPE);
        body.add(OAuth2ParameterNames.SCOPE,
                StringUtils.collectionToDelimitedString(registeredClient.getScopes(), " "));

        return restTemplate.postForEntity(tokenEndpoint, body, OAuth2AccessTokenResponse.class);

    }
    */
}