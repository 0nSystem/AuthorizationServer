package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "oauth2_authorization_consent", schema = "authorization")
public class Oauth2AuthorizationConsent {
    @EmbeddedId
    private Oauth2AuthorizationConsentId id;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "authorities", columnDefinition = "varchar [](200) not null")
    private String[] authorities;

}