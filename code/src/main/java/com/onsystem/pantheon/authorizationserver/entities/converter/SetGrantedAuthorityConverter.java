package com.onsystem.pantheon.authorizationserver.entities.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class SetGrantedAuthorityConverter implements AttributeConverter<Set<GrantedAuthority>, String> {
    @Override
    public String convertToDatabaseColumn(Set<GrantedAuthority> grantedAuthorities) {
        return StringUtils.collectionToDelimitedString(grantedAuthorities.stream().map(GrantedAuthority::getAuthority).toList(), ",");
    }

    @Override
    public Set<GrantedAuthority> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
