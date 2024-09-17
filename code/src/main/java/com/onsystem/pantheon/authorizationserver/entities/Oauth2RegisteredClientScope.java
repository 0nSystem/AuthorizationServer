package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_registered_client_scopes")
public class Oauth2RegisteredClientScope {
    @EmbeddedId
    private Oauth2RegisteredClientScopeId id;

    @MapsId("idRegisteredClient")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_registered_client", nullable = false)
    private Oauth2RegisteredClient idRegisteredClient;

    @MapsId("idScope")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_scope", nullable = false)
    private Oauth2Scope idOauth2Scope;

}