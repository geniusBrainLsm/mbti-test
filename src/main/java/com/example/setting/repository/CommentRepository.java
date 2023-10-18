package com.example.setting.repository;

import com.example.setting.entity.Comment;
import com.example.setting.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMemberMbti(String memberMbti);
    List<Comment> findByMemberNickname(String memberNickname);

}