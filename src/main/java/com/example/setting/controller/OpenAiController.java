package com.example.setting.controller;

import com.example.setting.dto.OpenAiRequest;
import com.example.setting.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/api/openai")
    public Mono<String> getMbtiResponse(@RequestBody OpenAiRequest request) {
        return openAiService.generateMbtiResponse(request);
    }
}