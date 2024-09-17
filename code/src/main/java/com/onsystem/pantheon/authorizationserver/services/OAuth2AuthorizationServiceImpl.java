package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.mapper.AMapperOAuth2Authorization;
import com.onsystem.pantheon.authorizationserver.repositories.OAuth2AuthorizationRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.UUID;

@Transactional
public class OAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private OAuth2AuthorizationRepository oAuth2AuthorizationRepository;
    private AMapperOAuth2Authorization aMapperOAuth2Authorization;

    public OAuth2AuthorizationServiceImpl(OAuth2AuthorizationRepository oAuth2AuthorizationRepository, AMapperOAuth2Authorization aMapperOAuth2Authorization) {
        this.oAuth2AuthorizationRepository = oAuth2AuthorizationRepository;
        this.aMapperOAuth2Authorization = aMapperOAuth2Authorization;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        final com.onsystem.pantheon.authorizationserver.entities.OAuth2Authorization oAuth2Authorization = aMapperOAuth2Authorization.toOAuth2Authorization(authorization);
        oAuth2AuthorizationRepository.save(oAuth2Authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        final com.onsystem.pantheon.authorizationserver.entities.OAuth2Authorization oAuth2Authorization = aMapperOAuth2Authorization.toOAuth2Authorization(authorization);
        oAuth2AuthorizationRepository.delete(oAuth2Authorization);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        final UUID uuid = UUID.fromString(id);

        return oAuth2AuthorizationRepository.findById(uuid)
                .map(aMapperOAuth2Authorization::toOAuth2Authorization)
                .orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(final @NotEmpty String token, final @Nullable OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        return oAuth2AuthorizationRepository.findOne(specificationFindByToken(token, tokenType))
                .map(aMapperOAuth2Authorization::toOAuth2Authorization)
                .orElse(null);
    }

    private Specification<com.onsystem.pantheon.authorizationserver.entities.OAuth2Authorization> specificationFindByToken(String token, @Nullable OAuth2TokenType tokenType) {
        return (root, query, criteriaBuilder) -> {
            if (tokenType == null) {
                final Predicate[] predicatesAllTokens = {
                        criteriaBuilder.equal(root.get("authorization_code_value"), token),
                        criteriaBuilder.equal(root.get("access_token_value"), token),
                        criteriaBuilder.equal(root.get("oidc_id_token_value"), token),
                        criteriaBuilder.equal(root.get("refresh_token_value"), token),
                        criteriaBuilder.equal(root.get("user_code_value"), token),
                        criteriaBuilder.equal(root.get("device_code_value"), token)
                };
                return criteriaBuilder.or(predicatesAllTokens);
            } else {
                return criteriaBuilder.equal(root.get(tokenType.getValue().concat("_value")), token);
            }

        };
    }
}
