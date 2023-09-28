package com.example.setting.controller;

import com.example.setting.entity.Comment;
import com.example.setting.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class BoardController {

    private final CommentRepository commentRepository;

    @Autowired
    public BoardController(CommentRepository replyRepository) {
        this.commentRepository = replyRepository;
    }

    @GetMapping("/notice_board")
    public String index() {
        return "notice_board"; // 예시로 notice_board.html로 이동하도록 설정
    }


//
//    @GetMapping("/notice_board/logout")
//    public String logout(HttpSession session) {
//        session.invalidate(); // 세션 무효화하여 로그아웃 처리
//        return "redirect: notice_board";
//    }

    @GetMapping("/setting/board/{mbti}")
    public String board(@PathVariable("mbti") String mbti, Model model, HttpSession session) {
        model.addAttribute("myMbti", mbti);
        session.setAttribute("myMbti", mbti); //  myMbti값을 세선에 넣고 gpt의 성격을 myMbti로 넘김
        return "notice_board";
    }
}
