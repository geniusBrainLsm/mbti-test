package com.example.setting.controller;

import com.example.setting.entity.Reply;
import com.example.setting.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class BoardController {

    private final ReplyRepository replyRepository;

    @Autowired
    public BoardController(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @GetMapping("/setting/board/{mbti}")
    public String board(@PathVariable("mbti") String mbti, Model model) {
        model.addAttribute("myMbti", mbti); // 자신의 MBTI 유형을 myMbti로 전달
        return "notice_board"; // 예시로 notice_board.html로 이동하도록 설정
    }

    @PostMapping("/setting/board/{mbti}/addReply")
    @ResponseBody
    public ResponseEntity<String> addReply(@PathVariable("mbti") String mbti,
                                           @RequestBody String content) {
        try {
            Reply reply = new Reply();
            reply.setMbtiType(mbti);
            reply.setContent(content);
            reply.setCreatedAt(new Date());
            replyRepository.save(reply);
            return ResponseEntity.ok("댓글 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록 실패");
        }
    }
}
