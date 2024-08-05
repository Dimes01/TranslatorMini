package com.example.tink_lab.services;

import com.example.tink_lab.configs.TranslationConfig;
import com.example.tink_lab.models.Language;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <b>Используется для обращения к внешнему сервису перевода</b>
 * <p>Имеет следующую функциональность:</p>
 * <ul>
 *     <li>Перевод текста с исходного языка на требуемый</li>
 *     <li>Парсинг данных из ответа внешнего сервиса</li>
 * </ul>
 */
@Service
public class TranslationService {
    /**
     * Конфигурация подключения к внешнему сервису
     */
    private final TranslationConfig trConf = new TranslationConfig();

    /**
     * Клиент для осуществления HTTP-запросов
     */
    private final RestTemplate restTemplate;

    /**
     * Инициализирует клиент для отправки запросов
     * @param restTemplateBuilder билдер для создания клиента для осуществления HTTP-запросов
     */
    public TranslationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Осуществляет запрос к внешнему сервису для перевода исходного текста
     * @param text Текст на исходном языке
     * @param sourceLang Исходный язык
     * @param targetLang Язык для перевода
     * @return Переведённый текст
     */
    public String Translate(String text, String sourceLang, String targetLang) {
        String url = String.format("%s/translate?sl=%s&dl=%s&text=%s", trConf.url, sourceLang, targetLang, text);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return GetTranslatedText(response.getBody());
    }

    /**
     * @return Список языков, доступных для перевода
     * @throws JsonProcessingException
     */
    public List<Language> GetPossibleLanguages() throws JsonProcessingException {
        String url = String.format("%s/languages", trConf.url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        List<Language> list;
        return ParseLanguages(response.toString());
    }

    /**
     * Осуществляет парсинг тела запроса
     * @param responseBody JSON в виде строки, содержащий поле "destination-text" (текст, переведённый внешним сервисом)
     * @return Содержимое поля "destination-text" либо пустую строку
     */
    public String GetTranslatedText(String responseBody) {
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

    /**
     * @param responseString строка, содержащая JSON
     * @return Список языков
     * @throws JsonProcessingException
     */
    private List<Language> ParseLanguages(String responseString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseString);

        List<Language> languages = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            languages.add(new Language(field.getKey(), field.getValue().asText()));
        }
        return languages;
    }
}
