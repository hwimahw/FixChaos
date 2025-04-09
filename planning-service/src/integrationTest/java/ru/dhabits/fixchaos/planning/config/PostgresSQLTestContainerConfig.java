package ru.dhabits.fixchaos.planning.config;

import org.testcontainers.containers.PostgreSQLContainer;

public final class PostgresSQLTestContainerConfig {

    private static final String DATABASE_NAME = "fixchaos";

    public static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.1")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);
    private PostgresSQLTestContainerConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
