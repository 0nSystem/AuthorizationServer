package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class Oauth2AuthorizationRedirectUrisId implements java.io.Serializable {
    private static final long serialVersionUID = 8752611266224851484L;
    @NotNull
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 1000)
    @NotNull
    @Column(name = "url", nullable = false, length = 1000)
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Oauth2AuthorizationRedirectUrisId entity = (Oauth2AuthorizationRedirectUrisId) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.url, entity.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

}