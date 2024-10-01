package com.onsystem.pantheon.authorizationserver.entities;

import com.onsystem.pantheon.authorizationserver.entities.converter.OAuth2AccessTokenTypeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "oauth2_authorization", schema = SCHEME_AUTHORIZATION)
public class Oauth2AuthorizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "registered_client_id", nullable = false)
    private UUID registeredClientId;

    @Size(max = 200)
    @NotNull
    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Size(max = 100)
    @NotNull
    @Column(name = "authorization_grant_type", nullable = false, length = 100)
    private String authorizationGrantType;

    @Size(max = 1000)
    @ColumnDefault("NULL")
    @Column(name = "authorized_scopes", columnDefinition = "varchar(50)[] not null", length = 1000)
    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] authorizedScopes;

    @Column(name = "attributes")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> attributes;

    @Size(max = 500)
    @ColumnDefault("NULL")
    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorization_code_value", length = Integer.MAX_VALUE)
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "authorization_code_metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> authorizationCodeMetadata;

    @Column(name = "access_token_value", length = Integer.MAX_VALUE)
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Column(name = "access_token_metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> accessTokenMetadata;

    @Convert(converter = OAuth2AccessTokenTypeConverter.class)
    @Size(max = 100)
    @ColumnDefault("NULL")
    @Column(name = "access_token_type", length = 100)
    private OAuth2AccessToken.TokenType accessTokenType;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @ColumnDefault("NULL")
    @Column(name = "access_token_scopes", columnDefinition = "varchar [](50)")
    private Set<String> accessTokenScopes;

    @Column(name = "oidc_id_token_value", length = Integer.MAX_VALUE)
    private String oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;

    @Column(name = "oidc_id_token_metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> oidcIdTokenMetadata;

    @Column(name = "refresh_token_value", length = Integer.MAX_VALUE)
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    @Column(name = "refresh_token_metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> refreshTokenMetadata;

    @Column(name = "user_code_value", length = Integer.MAX_VALUE)
    private String userCodeValue;

    @Column(name = "user_code_issued_at")
    private Instant userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private Instant userCodeExpiresAt;

    @Column(name = "user_code_metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> userCodeMetadata;

    @Column(name = "device_code_value", length = Integer.MAX_VALUE)
    private String deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private Instant deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private Instant deviceCodeExpiresAt;

    @Column(name = "device_code_metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> deviceCodeMetadata;

}