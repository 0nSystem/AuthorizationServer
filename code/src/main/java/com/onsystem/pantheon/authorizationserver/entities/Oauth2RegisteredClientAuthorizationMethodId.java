package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class Oauth2RegisteredClientAuthorizationMethodId implements java.io.Serializable {
    private static final long serialVersionUID = 7307962916313448940L;
    @NotNull
    @Column(name = "oauth2_registered_client_id", nullable = false)
    private UUID oauth2RegisteredClientId;

    @NotNull
    @Column(name = "authorization_methods_id", nullable = false)
    private Integer authorizationMethodsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Oauth2RegisteredClientAuthorizationMethodId entity = (Oauth2RegisteredClientAuthorizationMethodId) o;
        return Objects.equals(this.authorizationMethodsId, entity.authorizationMethodsId) &&
                Objects.equals(this.oauth2RegisteredClientId, entity.oauth2RegisteredClientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorizationMethodsId, oauth2RegisteredClientId);
    }

}