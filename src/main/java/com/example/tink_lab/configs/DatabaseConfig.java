package com.example.tink_lab.configs;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    public final String url = "jdbc:h2:mem:test";
    public final String user = "user";
    public final String password = "password";
    public final String driverClassName = "org.h2.Driver";
}
