package com.onsystem.pantheon.authorizationserver.integration.authorization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"postgres"})
public class TokenEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthorizationServerSettings authorizationServerSettings;


    @Test
    public void whenRequestTokenPostAndClientCredentialsIsCorrect() throws Exception {
        final ResponseEntity<String> response = postClientCredentialsCorrectRequest(authorizationServerSettings.getTokenEndpoint());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        validationAccessToken(response.getBody());

    }

    public void validationAccessToken(String accessTokenResponse) throws IOException {
        final Map<String, Object> body = RequestUtils.readString(accessTokenResponse);
        Assertions.assertNotNull(accessTokenResponse);
        Assertions.assertNotNull(body.get(OAuth2ParameterNames.ACCESS_TOKEN));
        Assertions.assertEquals("Bearer", body.get(OAuth2ParameterNames.TOKEN_TYPE));
        Assertions.assertNotNull(body.get(OAuth2ParameterNames.EXPIRES_IN));
    }


    /*
     * Token Refresh
     */



    /*
    Exchange token
     */


    private ResponseEntity<String> postClientCredentialsCorrectRequest(final String tokenEndpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2ParameterNames.GRANT_TYPE, "client_credentials");
        body.add(OAuth2ParameterNames.USERNAME, "onsystem_name");
        body.add(OAuth2ParameterNames.PASSWORD, "password");
        body.add(OAuth2ParameterNames.CLIENT_ID, "srvauthorizationserver");
        body.add(OAuth2ParameterNames.CLIENT_SECRET, "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForEntity(tokenEndpoint, request, String.class);
    }

    public ResultActions validationTokenWhenCorrectResult(ResultActions resultActions) throws Exception {
        return resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("token_type").value("Bearer"))
                .andExpect(MockMvcResultMatchers.jsonPath("expires_in").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("access_token").isNotEmpty());
    }
}
