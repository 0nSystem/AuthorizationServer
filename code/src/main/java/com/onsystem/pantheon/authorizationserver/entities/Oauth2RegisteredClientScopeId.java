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
public class Oauth2RegisteredClientScopeId implements java.io.Serializable {
    private static final long serialVersionUID = 8330177194521937556L;
    @NotNull
    @Column(name = "id_registered_client", nullable = false)
    private UUID idRegisteredClient;

    @NotNull
    @Column(name = "id_scope", nullable = false)
    private Integer idScope;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Oauth2RegisteredClientScopeId entity = (Oauth2RegisteredClientScopeId) o;
        return Objects.equals(this.idScope, entity.idScope) &&
                Objects.equals(this.idRegisteredClient, entity.idRegisteredClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idScope, idRegisteredClient);
    }

}