package com.example.setting.entity;// Comment.java
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String memberMbti;
    private LocalDateTime timestamp;
    private String memberNickname;

    private Long likes;

    @Version
    private Long version;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public void increaseLikes() { this.likes++; }

    public void decreaseLikes() { if (this.likes > 0) this.likes--; }
}
