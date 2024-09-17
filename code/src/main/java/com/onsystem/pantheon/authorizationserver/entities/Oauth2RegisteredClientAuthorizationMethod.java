package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_registered_client_authorization_methods")
public class Oauth2RegisteredClientAuthorizationMethod {
    @EmbeddedId
    private Oauth2RegisteredClientAuthorizationMethodId id;

    @MapsId("oauth2RegisteredClientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oauth2_registered_client_id", nullable = false)
    private Oauth2RegisteredClient oauth2RegisteredClient;

    @MapsId("authorizationMethodsId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "authorization_methods_id", nullable = false)
    private Oauth2AuthorizationMethod authorizationMethods;

}