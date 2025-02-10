package com.thiago.orderprocessor.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(SpringExtension.class)
public abstract class BaseTestContainer {
    
    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.0"))
                    .withDatabaseName("test_db")
                    .withUsername("test_user")
                    .withPassword("test_pass");
    
    @BeforeAll
    static void startContainer() {
        POSTGRES_CONTAINER.start();
    }
    
    static {
        POSTGRES_CONTAINER.start();
    }
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = POSTGRES_CONTAINER.getJdbcUrl();
        String username = POSTGRES_CONTAINER.getUsername();
        String password = POSTGRES_CONTAINER.getPassword();
        
        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", () -> username);
        registry.add("spring.datasource.password", () -> password);
    }
    
    @AfterAll
    static void stopContainer() {
        POSTGRES_CONTAINER.stop();
    }
}