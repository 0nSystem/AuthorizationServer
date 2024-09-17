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
public class Oauth2RegisteredClientAuthorizationGrantTypeId implements java.io.Serializable {
    private static final long serialVersionUID = -9050225358820272972L;
    @NotNull
    @Column(name = "oauth2_registered_client_id", nullable = false)
    private UUID oauth2RegisteredClientId;

    @NotNull
    @Column(name = "authorization_grant_types_id", nullable = false)
    private Integer authorizationGrantTypesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Oauth2RegisteredClientAuthorizationGrantTypeId entity = (Oauth2RegisteredClientAuthorizationGrantTypeId) o;
        return Objects.equals(this.authorizationGrantTypesId, entity.authorizationGrantTypesId) &&
                Objects.equals(this.oauth2RegisteredClientId, entity.oauth2RegisteredClientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorizationGrantTypesId, oauth2RegisteredClientId);
    }

}