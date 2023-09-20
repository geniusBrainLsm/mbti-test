package com.example.setting.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {


    @GetMapping("/setting/board")
    public String index() {
        return "notice_board"; // 예시로 notice_board.html로 이동하도록 설정
    }


    @GetMapping("/setting/board/{mbti}")
    public String board(@PathVariable("mbti") String mbti, Model model) {
        model.addAttribute("myMbti", mbti); // 자신의 MBTI 유형을 myMbti로 전달
        return "notice_board"; // 예시로 notice_board.html로 이동하도록 설정
    }

}
