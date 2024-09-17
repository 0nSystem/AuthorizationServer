package com.onsystem.pantheon.authorizationserver.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class SetStrConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> stringSet) {
        return StringUtils.join(stringSet, ",");
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .collect(Collectors.toSet());
    }
}
