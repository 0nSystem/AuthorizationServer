package com.onsystem.pantheon.authorizationserver.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ArrayConverter<T> implements AttributeConverter<T[], T> {
}
