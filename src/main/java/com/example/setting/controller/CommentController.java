package com.example.setting.controller;// CommentController.java
import com.example.setting.entity.Comment;
import com.example.setting.service.CommentService;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService; // 사용자 서비스

    @GetMapping("/{mbtiType}")
    public List<Comment> getComments(@PathVariable String mbtiType) {
        return commentService.getCommentsByMbtiType(mbtiType);
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment, HttpSession session) {
        // 현재 로그인한 사용자의 닉네임 가져오기
        String loggedNickname = (String) session.getAttribute("loggedNickname");

        // 로그인한 사용자의 닉네임이 있을 경우에만 댓글 작성
        if (loggedNickname != null) {
            return commentService.addComment(comment, loggedNickname);
        } else {
            // 로그인한 사용자가 없는 경우 예외 처리 또는 다른 처리 수행
            // ...
        }
        return null;
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId, HttpSession session) {
        // 현재 로그인한 사용자의 닉네임 가져오기
        String loggedNickname = (String) session.getAttribute("loggedNickname");

        if (loggedNickname != null) {
                commentService.likeComment(commentId,loggedNickname);
                return ResponseEntity.ok("Liked successfully");
            } else {
                return ResponseEntity.badRequest().body("You have already liked this comment");
            }
        }
 }

