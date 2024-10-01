package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "oauth2_authorization_consent", schema = SCHEME_AUTHORIZATION)
public class OAuth2AuthorizationConsentEntity {
    @EmbeddedId
    private Oauth2AuthorizationConsentId id;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "authorities", columnDefinition = "varchar [](200) not null")
    private Set<String> authorities;

}