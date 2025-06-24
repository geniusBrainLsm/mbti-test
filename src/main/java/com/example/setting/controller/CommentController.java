package com.example.setting.controller;// CommentController.java

import com.example.setting.dto.CommentDTO;
import com.example.setting.entity.Comment;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.CommentRepository;
import com.example.setting.repository.MemberRepository;
import com.example.setting.service.CommentService;
import com.example.setting.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/replies")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{memberMbti}")
    public List<CommentDTO> getComments(@PathVariable String memberMbti) {
        return commentService.getCommentsByMemberMbti(memberMbti);
    }

    @GetMapping("/my-comments")
    public ResponseEntity<List<CommentDTO>> getMyComments(Principal principal) {
        return ResponseEntity.ok(commentService.getMyComments(principal));
    }
    @PostMapping
    public ResponseEntity<Long> addComment(@RequestBody Long commentId, Principal principal) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.addComment(commentId, principal));
    }
    @PostMapping("/{commentId}/like")
    public ResponseEntity<Long> likeComment(@PathVariable Long commentId, Principal principal) {
        return ResponseEntity.ok(commentService.likeComment(commentId, principal));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long commentId, Principal principal) {
        return ResponseEntity.ok(commentService.deleteComment(commentId, principal));
    }
}