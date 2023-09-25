package com.example.setting.service;// CommentService.java
import com.example.setting.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByMemberMbti(String memberMbti);
    Comment addComment(Comment comment, String nickname);
    void likeComment(Long commentId,String nickname); // 댓글 추천 기능 추가


}