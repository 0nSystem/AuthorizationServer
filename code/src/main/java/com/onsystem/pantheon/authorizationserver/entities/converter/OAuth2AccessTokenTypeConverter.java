package com.onsystem.pantheon.authorizationserver.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.StringUtils;

@Converter(autoApply = true)
public class OAuth2AccessTokenTypeConverter implements AttributeConverter<OAuth2AccessToken.TokenType, String> {
    @Override
    public String convertToDatabaseColumn(OAuth2AccessToken.TokenType tokenType) {
        return tokenType.getValue();
    }

    @Override
    public OAuth2AccessToken.TokenType convertToEntityAttribute(String s) {
        if (StringUtils.hasText(s) && s.equalsIgnoreCase(OAuth2AccessToken.TokenType.BEARER.getValue())) {
            return OAuth2AccessToken.TokenType.BEARER;
        }
        return null;
    }
}
