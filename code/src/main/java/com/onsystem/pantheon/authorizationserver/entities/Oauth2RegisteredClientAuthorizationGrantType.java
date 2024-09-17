package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION,name = "oauth2_registered_client_authorization_grant_types")
public class Oauth2RegisteredClientAuthorizationGrantType {
    @EmbeddedId
    private Oauth2RegisteredClientAuthorizationGrantTypeId id;

    @MapsId("oauth2RegisteredClientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "oauth2_registered_client_id", nullable = false)
    private Oauth2RegisteredClient oauth2RegisteredClient;

    @MapsId("authorizationGrantTypesId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "authorization_grant_types_id", nullable = false)
    private Oauth2AuthorizationGrantType authorizationGrantTypes;

}