package com.example.tink_lab.controllers;

import com.example.tink_lab.models  .RequestTranslate;
import com.example.tink_lab.services.RequestService;
import com.example.tink_lab.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/translate")
public class TranslateController {
    private final TranslationService translationService;
    private final RequestService requestService;
    private final ExecutorService executorService;

    public TranslateController(TranslationService translationService, RequestService requestService) {
        this.translationService = translationService;
        this.requestService = requestService;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    @PostMapping
    public ResponseEntity<String> Translate(@RequestBody RequestTranslate requestBody, HttpServletRequest request) {
        var words = requestBody.GetText().split(" ");
        var futures = new ArrayDeque<Future<String>>();
        for (var word : words) {
            futures.add(executorService.submit(() -> translationService.Translate(word, requestBody.GetSourceLanguage(), requestBody.GetTargetLanguage())));
        }

        var translatedText = new StringBuilder();
        for (var future : futures) {
            String str = null;
            try {
                str = future.get();
            } catch (InterruptedException | ExecutionException e) {
                var cause = e.getCause();
                throw new RuntimeException(e);
            }
            translatedText.append(str);
            translatedText.append(" ");
        }

        var result = translatedText.toString();
        requestService.SaveRequest(request.getRemoteAddr(), requestBody.GetText(), result);

        return ResponseEntity.ok(result);
    }
}
