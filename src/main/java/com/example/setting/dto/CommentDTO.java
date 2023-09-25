package com.example.setting.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private String memberMbti;
    private Date timestamp;
    private String memberNickname;
    private int likes;
    private String likesNickname;

    public boolean hasLikedByUser(String userNickname) {
        return likesNickname != null && likesNickname.equals(userNickname);
    }
}
