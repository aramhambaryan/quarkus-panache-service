package org.hotel_booking.config;

import io.quarkus.runtime.StartupEvent;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;

@Slf4j
public class LiquibaseConfig {
    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String datasourceUrl;
    @ConfigProperty(name = "quarkus.datasource.username")
    String datasourceUsername;
    @ConfigProperty(name = "quarkus.datasource.password")
    String datasourcePassword;
    @ConfigProperty(name = "quarkus.liquibase.change-log")
    String changeLogLocation;
    @ConfigProperty(name = "quarkus.liquibase.default-schema-name", defaultValue = "public")
    String defaultSchemaName;
    @ConfigProperty(name = "quarkus.liquibase.migrate-at-start", defaultValue = "true")
    boolean migrateAtStart;

    public void runLiquibaseMigration(@Observes StartupEvent event) throws LiquibaseException {
        if (Boolean.TRUE.equals(migrateAtStart)) {
            ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader());
            DatabaseConnection conn = DatabaseFactory.getInstance()
                    .openConnection(datasourceUrl, datasourceUsername, datasourcePassword, null, resourceAccessor);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
            database.setDefaultSchemaName(defaultSchemaName);
            try (Liquibase liquibase = new Liquibase(changeLogLocation, resourceAccessor, database)) {
                liquibase.update(new Contexts(), new LabelExpression());
            } catch (Exception e) {
                log.error(String.format("Liquibase Migration Exception: %s", e.getMessage()), e);
            }
        }
    }
}
