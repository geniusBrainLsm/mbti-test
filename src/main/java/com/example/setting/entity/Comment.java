package com.example.setting.entity;// Comment.java
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String mbtiType;
    private Date timestamp; // 댓글 작성 시간
    private String nickname; // 사용자의 닉네임
    private int likes; // 추천 수
    private String likesNickname; //추천한 닉네임

    public boolean hasLikedByUser(String userNickname) {
        return likesNickname != null && likesNickname.equals(userNickname);
    }
}