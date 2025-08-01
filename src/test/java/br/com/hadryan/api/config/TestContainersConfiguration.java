package br.com.hadryan.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import static br.com.hadryan.api.config.IntegrationTestContainers.POSTGRE_SQL_CONTAINER;

@TestConfiguration(proxyBeanMethods = false)
@ImportTestcontainers(IntegrationTestContainers.class)
public class TestContainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return POSTGRE_SQL_CONTAINER;
    }
}
