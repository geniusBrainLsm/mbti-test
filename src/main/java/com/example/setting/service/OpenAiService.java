package com.example.setting.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final WebClient webClient;
    @Value("${openai.api.key}")
    private String apiKey;
    public OpenAiService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();
    }
    public Mono<String> generateMbtiResponse(MbtiType mbti, String userInput) {
        String systemPrompt = String.format(
                "너는 %s 유형의 성격을 가진 사람이야. 말투는 한국 커뮤니티 스타일로 하고, 실제로 그런 성격을 가진 사람처럼 말해.",
                mbti.toString()
        );

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1",
                "store", true,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userInput)
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.path("choices").get(0).path("message").path("content").asText());
    }





}
