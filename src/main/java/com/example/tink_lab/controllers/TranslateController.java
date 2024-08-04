package com.example.tink_lab.controllers;

import com.example.tink_lab.models.RequestDTO;
import com.example.tink_lab.models  .RequestTranslate;
import com.example.tink_lab.services.RequestService;
import com.example.tink_lab.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @CrossOrigin
    public ResponseEntity<RequestDTO> Translate(@RequestBody RequestTranslate requestBody, HttpServletRequest request) {
        var words = requestBody.GetText().split(" ");
        var futures = new ArrayDeque<Future<String>>();
        for (var word : words) {
            var future = executorService.submit(() -> translationService.Translate(word, requestBody.GetSourceLanguage(), requestBody.GetTargetLanguage()));
            futures.add(future);
        }

        var translatedText = new StringBuilder();
        for (var future : futures) {
            String str = null;
            try {
                str = future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getCause().toString());
            }
            translatedText.append(str);
            translatedText.append(" ");
        }

        var response = new RequestDTO(requestBody.GetText(), translatedText.toString().trim());
        requestService.SaveRequest(request.getRemoteAddr(), response.sourceText(), response.translatedText());

        return ResponseEntity.ok(response);
    }
}
