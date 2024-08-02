package ru.dhabits.fixchaos.trillioner.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestConfigHelper {

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = PostgresSQLTestContainerConfig.POSTGRES_SQL_CONTAINER;

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("CONTAINER.URL", postgreSQLContainer::getJdbcUrl);
        registry.add("CONTAINER.USERNAME", postgreSQLContainer::getUsername);
        registry.add("CONTAINER.PASSWORD", postgreSQLContainer::getPassword);
    }
}
