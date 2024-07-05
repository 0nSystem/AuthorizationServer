package com.onsystem.pantheon.authorizationserver.integration.authorization;

import com.onsystem.pantheon.authorizationserver.AuthorizationServerApplication;
import lombok.extern.slf4j.Slf4j;
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

    private static Stream<String> caseGetTokenWithGetRequest() {
        return Stream.of(
                "grant_type=client_credentials&client_id=srvauthorizationserver&client_secret=password"
        );
    }

    @ParameterizedTest
    @MethodSource
    public void caseGetTokenWithGetRequest(final String params) throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post(authorizationServerSettings.getTokenEndpoint())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content(params)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("token_type").value("Bearer"))
                .andExpect(MockMvcResultMatchers.jsonPath("expires_in").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("access_token").isNotEmpty());

    }


}
