package com.example.tink_lab.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    private String url = "jdbc:h2:mem:test";
    private String user = "user";
    private String password = "password";
    private String driverClassName = "org.h2.Driver";

    public String GetURL() { return url; }
    public void SetURL(String url) { this.url = url; }

    public String GetUser() { return user; }
    public void SetUser(String user) { this.user = user; }

    public String GetPassword() { return password; }
    public void SetPassword(String password) { this.password = password; }

    public String GetDriverClassName() { return driverClassName; }
    public void SetDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
}
