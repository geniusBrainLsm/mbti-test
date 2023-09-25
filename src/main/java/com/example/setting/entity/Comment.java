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
    private String memberMbti; //행님 이거 mbtiType에서 memberMbti로 다 바꿨습니다
    private Date timestamp; // 댓글 작성 시간
    private String memberNickname; // 사용자의 닉네임
    private int likes; // 추천 수
    private String likesNickname; //추천한 닉네임

    public boolean hasLikedByUser(String userNickname) {
        return likesNickname != null && likesNickname.equals(userNickname);
    }
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity member;
}