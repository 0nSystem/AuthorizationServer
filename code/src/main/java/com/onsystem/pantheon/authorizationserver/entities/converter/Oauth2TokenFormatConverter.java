package com.onsystem.pantheon.authorizationserver.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

@Converter(autoApply = true)
public class Oauth2TokenFormatConverter implements AttributeConverter<OAuth2TokenFormat,String> {
    @Override
    public String convertToDatabaseColumn(OAuth2TokenFormat oAuth2TokenFormat) {
        return oAuth2TokenFormat.getValue();
    }

    @Override
    public OAuth2TokenFormat convertToEntityAttribute(String s) {
        return new OAuth2TokenFormat(s);
    }
}
