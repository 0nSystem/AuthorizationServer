package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "oauth2_registered_client", schema = "authorization")
public class Oauth2RegisteredClient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 100)
    @NotNull
    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "client_id_issued_at", nullable = false)
    private Instant clientIdIssuedAt;

    @Size(max = 200)
    @ColumnDefault("NULL")
    @Column(name = "client_secret", length = 200)
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Size(max = 200)
    @NotNull
    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "client_authentication_methods", columnDefinition = "varchar [](50) not null")
    private String[] clientAuthenticationMethods;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "authorization_grant_types", columnDefinition = "varchar [](50) not null")
    private String[] authorizationGrantTypes;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @ColumnDefault("NULL")
    @Column(name = "redirect_uris", columnDefinition = "varchar [](1000)")
    private String[] redirectUris;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @ColumnDefault("NULL")
    @Column(name = "post_logout_redirect_uris", columnDefinition = "varchar [](1000)")
    private String[] postLogoutRedirectUris;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "scopes", columnDefinition = "varchar [](50) not null")
    private String[] scopes;

    @NotNull
    @Column(name = "client_settings", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> clientSettings;

    @NotNull
    @Column(name = "token_settings", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> tokenSettings;

}