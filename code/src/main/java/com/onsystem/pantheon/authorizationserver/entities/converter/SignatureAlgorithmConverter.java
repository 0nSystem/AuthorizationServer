package com.onsystem.pantheon.authorizationserver.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;

@Converter(autoApply = true)
public class SignatureAlgorithmConverter implements AttributeConverter<SignatureAlgorithm, String> {

    @Override
    public String convertToDatabaseColumn(SignatureAlgorithm signatureAlgorithm) {
        return signatureAlgorithm.getName();
    }

    @Override
    public SignatureAlgorithm convertToEntityAttribute(String s) {
        return SignatureAlgorithm.from(s);
    }
}
