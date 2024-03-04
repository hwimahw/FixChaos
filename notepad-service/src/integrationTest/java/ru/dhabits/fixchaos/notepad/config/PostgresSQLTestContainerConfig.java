package ru.dhabits.fixchaos.notepad.config;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

public final class PostgresSQLTestContainerConfig {

    private static final String DATABASE_NAME = "fixchaos";

    public static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.1")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withCopyFileToContainer(
            MountableFile.forClasspathResource("init_testcontainer_db.sql"),
                    "/docker-entrypoint-initdb.d/"
            );
    private PostgresSQLTestContainerConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
