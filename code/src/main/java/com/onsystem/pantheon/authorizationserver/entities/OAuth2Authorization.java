package com.onsystem.pantheon.authorizationserver.entities;

import com.onsystem.pantheon.authorizationserver.entities.converter.MapStrObjectConverter;
import com.onsystem.pantheon.authorizationserver.entities.converter.OAuth2AccessTokenTypeConverter;
import com.onsystem.pantheon.authorizationserver.entities.converter.SetStrConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Builder
@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_authorization")
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotNull
    @Size(max = 100)
    @Column(name = "registered_client_id", nullable = false)
    private UUID registeredClientId;

    @NotNull
    @Size(max = 100)
    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @NotNull
    @Size(max = 100)
    @Column(name = "authorization_grant_type", nullable = false)
    private String authorizationGrantType; //integer reference?

    @Size(max = 1000)
    @Column(name = "authorized_scopes")
    @Convert(converter = SetStrConverter.class)
    private Set<String> authorizedScopes;

    @Column(name = "attributes")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> attributes;

    @Size(max = 500)
    @Column(name = "state")
    private String state;

    @Column(name = "authorization_code_value")
    private String authorizationCodeValue;
    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;
    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;
    @Column(name = "authorization_code_metadata")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> authorizationCodeMetadata;

    @Column(name = "access_token_value")
    private String accessTokenValue;
    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;
    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;
    @Column(name = "access_token_metadata")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> accessTokenMetadata;
    @Convert(converter = OAuth2AccessTokenTypeConverter.class)
    @Size(max = 100)
    @Column(name = "access_token_type")
    private OAuth2AccessToken.TokenType accessTokenType;
    @Size(max = 1000)
    @Column(name = "access_token_scopes")
    private String accessTokenScopes;

    @Column(name = "oidc_id_token_value")
    private String oidcIdTokenValue;
    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;
    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;
    @Column(name = "oidc_id_token_metadata")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> oidcIdTokenMetadata;

    @Column(name = "refresh_token_value")
    private String refreshTokenValue;
    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;
    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;
    @Column(name = "refresh_token_metadata")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> refreshTokenMetadata;

    @Column(name = "user_code_value")
    private String userCodeValue;
    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;
    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;
    @Column(name = "user_code_metadata")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> userCodeMetadata;

    @Column(name = "device_code_value")
    private String deviceCodeValue;
    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;
    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;
    @Column(name = "device_code_metadata")
    @Convert(converter = MapStrObjectConverter.class)
    private Map<String, Object> deviceCodeMetadata;

}
