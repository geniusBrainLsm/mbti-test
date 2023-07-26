package com.example.setting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @GetMapping("/setting/questions")
    public String questions() {
        return "questions";
    }

    @GetMapping("/results.html")
    public String result(@RequestParam("mbti") String mbti, Model model) {
        model.addAttribute("mbti", mbti);
        return "results";
    }
}
