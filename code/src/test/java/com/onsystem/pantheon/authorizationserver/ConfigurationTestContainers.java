package com.onsystem.pantheon.authorizationserver;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ConfigurationTestContainers {


    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgres() {
        return new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("management")
                .withUsername("postgres")
                .withPassword("postgres")
                .withInitScript("sql/schema.sql");
    }
}
