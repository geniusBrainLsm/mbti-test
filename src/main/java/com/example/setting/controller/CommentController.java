package com.example.setting.controller;// CommentController.java
import com.example.setting.entity.Comment;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.CommentRepository;
import com.example.setting.repository.MemberRepository;
import com.example.setting.service.CommentService;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService; // 사용자 서비스

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/{memberMbti}")
    public List<Comment> getComments(@PathVariable String memberMbti) {
        return commentService.getCommentsByMemberMbti(memberMbti);
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment, Principal principal) {
        String username = principal.getName();  // 이메일로 조회

        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 로그인한 사용자의 닉네임이 있을 경우에만 댓글 작성
        if (member.getMemberNickname() != null) {
            comment.setMember(member);  // 댓글 작성자 설정
            return commentRepository.save(comment);  // 댓글 저장 및 반환
        }

        throw new IllegalArgumentException("User nickname is required to write a comment");
    }
    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId, Principal principal) {
        String username = principal.getName();  // 이메일로 조회

        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (member.getMemberNickname() != null) {
            commentService.likeComment(commentId,member.getMemberNickname());
            return ResponseEntity.ok("Liked successfully");
        } else {
            return ResponseEntity.badRequest().body("You have already liked this comment");
        }
    }
}