package com.onsystem.pantheon.authorizationserver.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2AuthorizationConsentId {
    @NotNull
    @Column(name = "registered_client_id", nullable = false)
    private UUID registeredClientId;
    @NotNull
    @Column(name = "principal_name", nullable = false)
    private String principalName;
}
