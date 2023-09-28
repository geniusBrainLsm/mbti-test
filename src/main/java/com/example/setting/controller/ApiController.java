package com.example.setting.controller;

import com.example.setting.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {


    private final OpenAiService openAiService;

    public ApiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/api/openai")
    public String getResponseFromOpenai(@RequestBody String userInput) {
        return openAiService.sendMessageToOpenai(userInput);
    }
}
