package com.thiago.orderprocessor.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("!test")
public class DataSourceConfig {
    
    @Value("${database.host}")
    private String host;
    @Value("${database.port}")
    private String port;
    @Value("${database.schema}")
    private String schema;
    @Value("${database.user}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${database.name}")
    private String name;
    
    
    @Bean
    @Primary
    public DataSource getDataSource() {
        
        final String url = STR."jdbc:postgresql://\{host}:\{port}/\{name}?currentSchema=\{schema}";
        
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setPoolName("authenticator");
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setLeakDetectionThreshold(20000);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMaxLifetime(1800000);
        return new HikariDataSource(hikariConfig);
    }
}
