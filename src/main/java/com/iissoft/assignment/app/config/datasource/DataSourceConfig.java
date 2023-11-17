package com.iissoft.assignment.app.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.postgres-datasource")
    public PostgresConfigProperties postgresConfigProperties() {
        var c = new PostgresConfigProperties();
        return new PostgresConfigProperties();
    }
}
