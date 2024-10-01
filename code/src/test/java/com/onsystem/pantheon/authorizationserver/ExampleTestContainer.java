package com.onsystem.pantheon.authorizationserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Import(ConfigurationTestContainers.class)
public class ExampleTestContainer {


    @Test
    public void testExample() {
        System.out.println("Hello: ");
    }
}
