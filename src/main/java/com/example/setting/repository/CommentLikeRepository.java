package com.example.setting.repository;

import com.example.setting.entity.Comment;
import com.example.setting.entity.CommentLike;
import com.example.setting.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByMemberAndComment(MemberEntity member, Comment comment);

    // 특정 회원과 댓글에 대한 좋아요 기록 삭제
    void deleteByMemberAndComment(MemberEntity member, Comment comment);}


