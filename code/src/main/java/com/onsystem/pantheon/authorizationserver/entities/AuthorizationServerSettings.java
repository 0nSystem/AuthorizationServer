package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorization_server_settings", schema = "authorization")
public class AuthorizationServerSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "issuer")
    private String issuer;

    @Size(max = 255)
    @NotNull
    @Column(name = "authorization_endpoint", nullable = false)
    private String authorizationEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "device_authorization_endpoint", nullable = false)
    private String deviceAuthorizationEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "device_verification_endpoint", nullable = false)
    private String deviceVerificationEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "token_endpoint", nullable = false)
    private String tokenEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "jwk_set_endpoint", nullable = false)
    private String jwkSetEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "token_revocation_endpoint", nullable = false)
    private String tokenRevocationEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "token_introspection_endpoint", nullable = false)
    private String tokenIntrospectionEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "oidc_client_registration_endpoint", nullable = false)
    private String oidcClientRegistrationEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "oidc_user_info_endpoint", nullable = false)
    private String oidcUserInfoEndpoint;

    @Size(max = 255)
    @NotNull
    @Column(name = "oidc_logout_endpoint", nullable = false)
    private String oidcLogoutEndpoint;

}