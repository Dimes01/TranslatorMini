package com.example.tink_lab.controllers;

import com.example.tink_lab.models.Language;
import com.example.tink_lab.models.RequestDTO;
import com.example.tink_lab.models.RequestLog;
import com.example.tink_lab.models  .RequestTranslate;
import com.example.tink_lab.services.RequestService;
import com.example.tink_lab.services.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <b>Контроллер, осуществляющий обработку запросов по пути /translated</b>
 */
@RestController
@RequestMapping("/translate")
@CrossOrigin
public class TranslateController {
    /**
     * Сервис перевода
     */
    private final TranslationService translationService;

    /**
     * Сервис сохранения информации о запросах
     */
    private final RequestService requestService;

    /**
     * Сервис, обеспечивающий многопоточную обработку запроса
     */
    private final ExecutorService executorService;

    /**
     * Инициализирует обязательные для работы контроллера сервисы
     * @param translationService Сервис для перевода через внешний сервис
     * @param requestService Сервис для логирования информации о запросе
     */
    public TranslateController(TranslationService translationService, RequestService requestService) {
        this.translationService = translationService;
        this.requestService = requestService;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    /**
     * Метод обрабатывает POST-запрос, содержащий информацию для перевода, в многопоточном режиме (пул до 10 потоков) с помощью интерфейса Future.
     * В случае ошибки выводит информацию о ней без прерывания.
     * @param requestBody тело запроса, содержащее информацию о тексте, исходном языке и языке для перевода
     * @param request контекст запроса
     * @return ответ (DTO запроса), содержащий информацию об исходном и переведённом текстах
     */
    @PostMapping
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
            } catch (InterruptedException e) {
                System.out.println(e.getCause().toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            } catch (ExecutionException e) {
                System.out.println(e.getCause().toString());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            translatedText.append(str);
            translatedText.append(" ");
        }

        var response = new RequestDTO(requestBody.GetText(), translatedText.toString().trim());
        try {
            requestService.SaveRequest(request.getRemoteAddr(), response.sourceText(), response.translatedText());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * @return Список всех логов запросов
     */
    @GetMapping("/get")
    public ResponseEntity<LinkedList<RequestDTO>> GetHistory() {
        LinkedList<RequestDTO> list;
        try {
            list = requestService.GetAllRequests();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> GetPossibleLanguages() {
        List<Language> list;
        try {
            list = translationService.GetPossibleLanguages();
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(list);
    }
}
