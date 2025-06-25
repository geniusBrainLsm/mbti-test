package com.example.setting.service;// CommentService.java
import com.example.setting.dto.CommentCreateRequest;
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

    public List<CommentDTO> getCommentsByMemberMbti(String memberMbti, String sort) {
        List<Comment> comments;

        switch (sort.toLowerCase()) {
            case "popular":
                comments = commentRepository.findByMemberMbtiOrderByLikesDesc(memberMbti);
                break;
            case "newest":
            default:
                comments = commentRepository.findByMemberMbtiOrderByTimestampDesc(memberMbti);
                break;
        }

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
            commentLikeRepository.save(like);
        } else {
            commentLikeRepository.deleteByMemberAndComment(member, comment);
            comment.decreaseLikes(); // 내부적으로 likes - 1

        }

        return comment.getLikes();
    }


    public Long deleteComment(Long commentId, Principal principal) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        MemberEntity member = memberRepository.findByMemberEmail(principal.getName()).orElseThrow(()->new IllegalArgumentException("유저없음"));
        if (!Objects.equals(member.getMemberNickname(), comment.getMemberNickname())) new IllegalArgumentException("자신의 댓글만 삭제가능");
        commentRepository.delete(comment);
        return commentId;
    }
    public CommentDTO createComment(String memberMbti, CommentCreateRequest request, Principal principal) {
        String email = principal.getName();

        MemberEntity member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (member.getMemberNickname() == null) {
            throw new IllegalArgumentException("User nickname is required to write a comment");
        }

        Comment comment = new Comment();
        comment.setText(request.getText()); // CommentCreateRequest에서 text 받음
        comment.setMemberMbti(memberMbti.toUpperCase());
        comment.setTimestamp(LocalDateTime.now());
        comment.setMemberNickname(member.getMemberNickname());
        comment.setMember(member); // 연관관계 설정

        Comment savedComment = commentRepository.save(comment);

        return new CommentDTO(savedComment);
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