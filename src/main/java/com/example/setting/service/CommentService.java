package com.example.setting.service;// CommentService.java
import com.example.setting.dto.CommentDTO;
import com.example.setting.entity.Comment;
import com.example.setting.entity.CommentLike;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.CommentLikeRepository;
import com.example.setting.repository.CommentRepository;
import com.example.setting.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CommentLikeRepository commentLikeRepository;

    public List<CommentDTO> getCommentsByMemberMbti(String memberMbti) {
        List<Comment> comments = commentRepository.findByMemberMbti(memberMbti);

        return comments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public Long likeComment(Long commentId, Principal principal) {
        String email = principal.getName();
        MemberEntity member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found: " + commentId));

        boolean alreadyLiked = commentLikeRepository.existsByMemberAndComment(member, comment);

        if (!alreadyLiked) {
            CommentLike like = new CommentLike();
            like.setMember(member);
            like.setComment(comment);
            comment.increaseLikes(); // 내부적으로 likes + 1
        } else {
            commentLikeRepository.deleteByMemberAndComment(member, comment);
            comment.decreaseLikes(); // 내부적으로 likes - 1
        }

        return commentId;
    }


    public Long deleteComment(Long commentId, Principal principal) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        MemberEntity member = memberRepository.findByMemberEmail(principal.getName()).orElseThrow(()->new IllegalArgumentException("유저없음"));
        if (!Objects.equals(member.getMemberNickname(), comment.getMemberNickname())) new IllegalArgumentException("자신의 댓글만 삭제가능");
        commentRepository.delete(comment);
        return commentId;
    }
    public Long addComment(@RequestBody Long commentId, Principal principal) {
        String username = principal.getName();  // 이메일로 조회

        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("댓글없음"));

        // 로그인한 사용자의 닉네임이 있을 경우에만 댓글 작성
        if (member.getMemberNickname() != null) {
            comment.setMemberNickname(member.getMemberNickname());  // 댓글 작성자 설정
            commentRepository.save(comment);  // 댓글 저장 및 반환
            return comment.getId();
        }else {
            throw new IllegalArgumentException("User nickname is required to write a comment");
        }

    }
    public List<CommentDTO> getMyComments(Principal principal){
        String username = principal.getName();  // 이메일로 조회
        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String currentMemberNickname = member.getMemberNickname();
        List<Comment> comments = commentRepository.findByMemberNickname(currentMemberNickname);
        return comments.stream().map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}