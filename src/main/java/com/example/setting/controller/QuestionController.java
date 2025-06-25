package com.example.setting.controller;

import com.example.setting.service.MbtiType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @GetMapping("/questions")
    public String questions() {
        return "questions";
    }

    @GetMapping("/results")
    public String result(@RequestParam("mbti") MbtiType mbti, Model model) {
        model.addAttribute("mbti", mbti);
        return "results";
    }
}
