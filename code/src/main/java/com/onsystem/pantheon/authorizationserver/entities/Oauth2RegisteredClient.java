package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Builder
@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_registered_client")
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2RegisteredClient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "client_id_issued_at", nullable = false)
    private Instant clientIdIssuedAt;

    @Size(max = 200)
    @NotNull
    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;

    @OneToOne(mappedBy = "registeredClient")
    private Oauth2RegisteredClientAuthorizationClientSetting clientSettings;

    @OneToOne(mappedBy = "registeredClient")
    private Oauth2RegisteredClientTokenSetting tokenSettings;

    @ManyToMany
    @JoinTable(
            schema = SCHEME_AUTHORIZATION,
            name = "oauth2_registered_client_scopes",
            joinColumns = @JoinColumn(name = "id_registered_client"),
            inverseJoinColumns = @JoinColumn(name = "id_scope"))
    private Set<Oauth2Scope> scopes;

    @ManyToMany
    @JoinTable(
            schema = SCHEME_AUTHORIZATION,
            name = "oauth2_registered_client_authorization_methods",
            joinColumns = @JoinColumn(name = "oauth2_registered_client_id"),
            inverseJoinColumns = @JoinColumn(name = "authorization_methods_id")
    )
    private Set<Oauth2AuthorizationMethod> authorizationMethods;

    @ManyToMany
    @JoinTable(
            schema = SCHEME_AUTHORIZATION,
            name = "oauth2_registered_client_authorization_grant_types",
            joinColumns = @JoinColumn(name = "oauth2_registered_client_id"),
            inverseJoinColumns = @JoinColumn(name = "authorization_grant_types_id")
    )
    private Set<Oauth2AuthorizationGrantType> grantTypes;

    @OneToMany(mappedBy = "oauth2RegisteredClient")
    private Set<Oauth2AuthorizationRedirectUris> redirectUris;

    @OneToMany(mappedBy = "oauth2RegisteredClient")
    private Set<Oauth2AuthorizationPostLogoutRedirectUris> logoutRedirectUris;
}