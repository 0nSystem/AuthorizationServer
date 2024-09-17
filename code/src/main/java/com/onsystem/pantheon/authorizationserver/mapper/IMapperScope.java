package com.onsystem.pantheon.authorizationserver.mapper;

import com.onsystem.pantheon.authorizationserver.entities.Oauth2Scope;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IMapperScope {


    default String toStr(Oauth2Scope oauth2Scope) {
        return oauth2Scope.getName();
    }

    default Oauth2Scope toScope(String scope) {
        return Oauth2Scope.builder()
                .name(scope)
                .build();
    }

    @InheritConfiguration
    List<String> toStr(Collection<Oauth2Scope> oauth2Scopes);

    @InheritConfiguration
    Set<Oauth2Scope> oauth2Scopes(Collection<String> scopes);
}
