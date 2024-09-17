package com.onsystem.pantheon.authorizationserver.entities;

import com.onsystem.pantheon.authorizationserver.entities.converter.Oauth2TokenFormatConverter;
import com.onsystem.pantheon.authorizationserver.entities.converter.SignatureAlgorithmConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import static com.onsystem.pantheon.authorizationserver.Constans.SCHEME_AUTHORIZATION;

@Getter
@Setter
@Entity
@Table(schema = SCHEME_AUTHORIZATION, name = "oauth2_registered_client_token_settings")
public class Oauth2RegisteredClientTokenSetting {

    @Id
    @OneToOne
    @JoinColumn(name = "registered_client_id", unique = true)
    private Oauth2RegisteredClient registeredClient;

    @NotNull
    @Column(name = "authorization_code_time_to_live", nullable = false)
    private Double authorizationCodeTimeToLive;

    @NotNull
    @Column(name = "access_token_time_to_live", nullable = false)
    private Double accessTokenTimeToLive;

    @NotNull
    @Column(name = "device_code_time_to_live", nullable = false)
    private Double deviceCodeTimeToLive;

    @NotNull
    @Column(name = "reuse_refresh_tokens", nullable = false)
    private Boolean reuseRefreshTokens = false;
    @NotNull
    @Column(name = "refresh_token_time_to_live", nullable = false)
    private Double refreshTokenTimeToLive;

    @Convert(converter = Oauth2TokenFormatConverter.class)
    @Column(name = "access_token_format", columnDefinition = "varchar(255) not null")
    private OAuth2TokenFormat accessTokenFormat;

    @Enumerated(EnumType.STRING)
    //@Convert(converter = SignatureAlgorithmConverter.class)
    @Column(name = "id_token_signature_algorithm", columnDefinition = "signature_algorithm not null")
    private SignatureAlgorithm idTokenSignatureAlgorithm;
}