package com.example.tink_lab.services;

import com.example.tink_lab.models.RequestLog;
import com.example.tink_lab.repositories.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * <b>Используется для взаимодействия с репозиторием</b>
 * <p>Имеет следующий функционал:</p>
 * <ul>
 *     <li>Создание лога запроса</li>
 *     <li>Отправка лога в репозиторий</li>
 * </ul>
 */
@Service
public class RequestService {
    /**
     * Репозиторий для сохранения логов
     */
    private final RequestRepository repository;

    /**
     * Инициализирует репозиторий
     * @param repository Репозиторий для сохранения логов
     */
    public RequestService(RequestRepository repository) {
        this.repository = repository;
    }

    /**
     * Создаёт лог и отправляет его в репозиторий
     * @param ip IP-адрес запроса
     * @param sourceText Текст на исходном языке
     * @param translatedText Переведенный текст
     */
    public void SaveRequest(String ip, String sourceText, String translatedText) {
        RequestLog log = new RequestLog(sourceText, translatedText, ip);
        repository.SaveRequest(log);
    }

    /**
     * @return Список всех логов запросов из репозитория
     */
    public LinkedList<RequestLog> GetAllRequests() {
        return repository.GetAllRequests();
    }
}
