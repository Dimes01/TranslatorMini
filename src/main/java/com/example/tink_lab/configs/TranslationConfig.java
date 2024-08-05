package com.example.tink_lab.configs;

import org.springframework.context.annotation.Configuration;

/**
 * <b>Используется для конфигурации подключения к внешнему сервису перевода</b>
 * <p>Сохраняется следующая информация:</p>
 * <ul>
 *     <li>url (url для подключения к сервису)</li>
 * </ul>
 * <b>!!!Для подключения именно к этому сервису никаких ApiKey не нужно</b>
 */
@Configuration
public class TranslationConfig {
    public final String url = "https://ftapi.pythonanywhere.com";
}
