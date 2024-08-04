package com.example.tink_lab.services;

import com.example.tink_lab.models.RequestLog;
import com.example.tink_lab.repositories.RequestRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private final RequestRepository repository;

    public RequestService(RequestRepository repository) {
        this.repository = repository;
    }

    public void SaveRequest(String ip, String sourceText, String translatedText) {
        RequestLog log = new RequestLog(sourceText, translatedText, ip);
        repository.SaveRequest(log);
    }
}
