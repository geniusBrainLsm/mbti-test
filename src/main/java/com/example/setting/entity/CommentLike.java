package com.example.setting.entity;

import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Table(name = "comment_like",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "comment_id"}))
@EntityListeners(AuditingEntityListener.class)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @CreatedDate
    @Column(name = "liked_at", nullable = false, updatable = false)
    private LocalDateTime likedAt;

    @PrePersist
    private void onCreate() {
        this.likedAt = LocalDateTime.now();
    }

}