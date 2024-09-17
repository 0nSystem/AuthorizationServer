package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION,name = "oauth2_authorization_post_logout_redirect_uris")
public class Oauth2AuthorizationPostLogoutRedirectUris {
    @EmbeddedId
    private Oauth2AuthorizationPostLogoutRedirectUrisId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Oauth2RegisteredClient oauth2RegisteredClient;

}