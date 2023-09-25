package com.example.setting.repository;

import com.example.setting.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Correct import for List

@Repository
public interface ReplyRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMbtiType(String mbtiType);
}
