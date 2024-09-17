package com.onsystem.pantheon.authorizationserver.entities;

import com.onsystem.pantheon.authorizationserver.entities.converter.SignatureAlgorithmConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Builder
@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_registered_client_authorization_client_settings")
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2RegisteredClientAuthorizationClientSetting {
    @Id
    @OneToOne
    @JoinColumn(name = "registered_client_id", unique = true)
    private Oauth2RegisteredClient registeredClient;

    @ColumnDefault("false")
    @Column(name = "require_proof_key")
    private Boolean requireProofKey;

    @ColumnDefault("true")
    @Column(name = "require_authorization_consent")
    private Boolean requireAuthorizationConsent;

    @Size(max = 1000)
    @Column(name = "jwt_set_url", length = 1000)
    private String jwtSetUrl;

    @Size(max = 100)
    @Column(name = "token_endpoint_authentication_signing_algorithm", length = 100)
    @Enumerated(EnumType.STRING)
    //@Convert(converter = SignatureAlgorithmConverter.class)
    private SignatureAlgorithm tokenEndpointAuthenticationSigningAlgorithm;

}