package com.onsystem.pantheon.authorizationserver.repositories;

import com.onsystem.pantheon.authorizationserver.entities.AuthorizationServerSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "auth.mock", havingValue = "false")
public interface AuthorizationSettingsRepository extends JpaRepository<AuthorizationServerSettings, Integer> {

}
