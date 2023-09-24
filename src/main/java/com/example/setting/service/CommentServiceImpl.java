package com.example.setting.service;// CommentServiceImpl.java
import com.example.setting.entity.Comment;
import com.example.setting.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByMbtiType(String mbtiType) {
        return commentRepository.findByMbtiType(mbtiType);
    }

    @Override
    public Comment addComment(Comment comment, String nickname) {
        comment.setTimestamp(new Date()); // 댓글 작성 시간 설정
        comment.setNickname(nickname); // 사용자의 닉네임 설정
        comment.setLikes(0); // 초기 추천 수 설정
        return commentRepository.save(comment);
    }

    @Override
    public void likeComment(Long commentId, String nickname) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null) {
            // 이미 추천한 사용자인지 확인
            if (comment.getLikesNickname() == null || !comment.getLikesNickname().contains(nickname)) {
                // 이미 추천한 사용자가 아닌 경우에만 추천을 추가
                comment.setLikes(comment.getLikes() + 1); // 추천 수 증가
                // 추천한 닉네임을 기록
                if (comment.getLikesNickname() == null) {
                    comment.setLikesNickname(nickname);
                } else {
                    comment.setLikesNickname(comment.getLikesNickname() + "," + nickname);
                }
                commentRepository.save(comment);
            }
        }
    }
}