package com.onsystem.pantheon.authorizationserver.entities;


import com.onsystem.pantheon.authorizationserver.entities.converter.SetGrantedAuthorityConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Table(schema = SCHEME_AUTHORIZATION,name = "oauth2_authorization_consent")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuth2AuthorizationConsent {

    @EmbeddedId
    private OAuth2AuthorizationConsentId id;

    @Column(name = "authorities")
    @Convert(converter = SetGrantedAuthorityConverter.class)
    private Set<GrantedAuthority> authorities;

    //TODO scopes?

}
