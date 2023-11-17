package com.iissoft.assignment.app.config.datasource;

import lombok.Data;

@Data
public class PostgresConfigProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
