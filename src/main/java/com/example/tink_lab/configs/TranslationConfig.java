package com.example.tink_lab.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranslationConfig {
    private String url = "https://ftapi.pythonanywhere.com/translate";

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
