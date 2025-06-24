package com.example.setting.controller;

import com.example.setting.dto.OpenAiRequest;
import com.example.setting.service.MbtiType;
import com.example.setting.service.OpenAiService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice_board")
public class BoardController {
    private final OpenAiService openAiService;
    @GetMapping
    public String index() {
        return "notice_board";
    }
    @PostMapping("/{mbti}")
    public Mono<String> getResponseFromOpenai(
            @RequestBody OpenAiRequest request) {
        return openAiService.generateMbtiResponse(request);
    }

    @GetMapping("/{mbti}")
    public String noticeBoardByMbti(@PathVariable MbtiType mbti, Model model) {
        model.addAttribute("mbti", mbti);
        return "notice_board";
    }

}
