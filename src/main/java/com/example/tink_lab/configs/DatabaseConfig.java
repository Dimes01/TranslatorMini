package com.example.tink_lab.configs;

import org.springframework.context.annotation.Configuration;

/**
 * <b>Используется для конфигурации подключения к БД</b>
 * <p>Сохраняется следующая информация:</p>
 * <li>url (url для подключения к БД)</li>
 * <li>user (имя пользователя)</li>
 * <li>password (пароль пользователя)</li>
 * <li>driverClassName (название драйвера БД для использования в JDBC)</li>
 */
@Configuration
public class DatabaseConfig {
    public final String url = "jdbc:h2:mem:test";
    public final String user = "user";
    public final String password = "password";
    public final String driverClassName = "org.h2.Driver";
}
