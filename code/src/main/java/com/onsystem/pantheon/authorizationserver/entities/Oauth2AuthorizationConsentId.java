package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class Oauth2AuthorizationConsentId implements java.io.Serializable {
    private static final long serialVersionUID = -2951214057078222769L;
    @Size(max = 100)
    @NotNull
    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Size(max = 200)
    @NotNull
    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Oauth2AuthorizationConsentId entity = (Oauth2AuthorizationConsentId) o;
        return Objects.equals(this.registeredClientId, entity.registeredClientId) &&
                Objects.equals(this.principalName, entity.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredClientId, principalName);
    }

}