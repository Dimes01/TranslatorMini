package com.example.tink_lab;

import com.example.tink_lab.services.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TranslationServiceTests {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @InjectMocks
    private TranslationService translationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
    }

//    @Test
//    public void testTranslate() {
//        String mockResponse = "{\"destination-text\": \"Привет\"}";
//        when(restTemplate.getForEntity(anyString(), Class.class)).thenReturn(new ResponseEntity(mockResponse, HttpStatus.OK));
//        String result = translationService.Translate("Hello", "en", "ru");
//        assertEquals("Привет", result);
//    }

    @Test
    public void testGetTranslatedText() {
        String mockResponse = "{\"destination-text\": \"Привет\"}";
        String result = translationService.GetTranslatedText(mockResponse);
        assertEquals("Привет", result);
    }

    @Test
    public void testGetTranslatedTextWithInvalidJson() {
        String invalidJson = "Invalid JSON";
        String result = translationService.GetTranslatedText(invalidJson);
        assertEquals("", result);
    }
}
