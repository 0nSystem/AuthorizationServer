package com.onsystem.pantheon.authorizationserver.integration.authorization;

import com.onsystem.pantheon.authorizationserver.AuthorizationServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

@SpringBootTest(classes = AuthorizationServerApplication.class)
@ActiveProfiles(profiles = {"postgres"})
@AutoConfigureMockMvc
@Slf4j
public class TokenEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorizationServerSettings authorizationServerSettings;


    @Test
    public void caseGetTokenWithPostRequestAndGrantTypeClientCredential() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post(authorizationServerSettings.getTokenEndpoint())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content("grant_type=client_credentials& username=onsystem_name &password=password& client_id=srvauthorizationserver& client_secret=password")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("token_type").value("Bearer"))
                .andExpect(MockMvcResultMatchers.jsonPath("expires_in").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("access_token").isNotEmpty());
    }


    /*
        Example with basic header authorization
     */

    /*
    Refresh token
     */
}
