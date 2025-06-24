package com.example.setting.dto;

import com.example.setting.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private String memberMbti;
    private LocalDateTime timestamp;
    private String memberNickname;
    private int likes;
    private String likesNickname;
    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.memberMbti = comment.getMemberMbti();
        this.timestamp = comment.getTimestamp();
        this.memberNickname = comment.getMemberNickname();
        this.likes = comment.getLikes();
        this.likesNickname = comment.getMemberNickname();
    }


}
