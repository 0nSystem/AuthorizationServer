package com.onsystem.pantheon.authorizationserver.services;

import com.onsystem.pantheon.authorizationserver.entities.UserEntity;
import com.onsystem.pantheon.authorizationserver.mapper.IMapperUser;
import com.onsystem.pantheon.authorizationserver.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final IMapperUser iMapperUser;

    public UserDetailsServiceImpl(UserRepository userRepository, IMapperUser iMapperUser) {
        this.userRepository = userRepository;
        this.iMapperUser = iMapperUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity userEntity = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        return iMapperUser.toUserDTO(userEntity);
    }
}
