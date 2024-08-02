package com.example.tink_lab.services;

import com.example.tink_lab.configs.TranslationConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class TranslationService {
    private TranslationConfig trConf = new TranslationConfig();
    private final RestTemplate restTemplate;

    public TranslationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    // TODO: написать тест
    public String Translate(String text, String sourceLang, String targetLang) {
        String encodedText, encodedSourceLang, encodedTargetLang;
        try {
            encodedText = URLEncoder.encode(text, "UTF-8");
            encodedSourceLang = URLEncoder.encode(sourceLang, "UTF-8");
            encodedTargetLang = URLEncoder.encode(targetLang, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String url = String.format("%s?sl=%s&dl=%s&text=%s&", trConf.getUrl(), encodedSourceLang, encodedTargetLang, encodedText);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return GetTranslatedText(response.getBody());
    }

    // TODO: написать тест
    private String GetTranslatedText(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        String text = "";
        try {
            var node = mapper.readTree(responseBody);
            text = node.get("destination-text").asText();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            System.out.println("Incorrect JSON from translator");
        }
        return text;
    }
}
