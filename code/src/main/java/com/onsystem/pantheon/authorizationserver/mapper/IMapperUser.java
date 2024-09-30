package com.onsystem.pantheon.authorizationserver.mapper;


import com.onsystem.pantheon.authorizationserver.dto.UserDTO;
import com.onsystem.pantheon.authorizationserver.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IMapperUser {

    @Mapping(target = "enabled", ignore = true, defaultValue = "true")
    @Mapping(target = "credentialsNonExpired", ignore = true, defaultValue = "true")
    @Mapping(target = "accountNonLocked", ignore = true, defaultValue = "true")
    @Mapping(target = "accountNonExpired", ignore = true, defaultValue = "true")
    UserDTO toUserDTO(UserEntity userEntity);
}
