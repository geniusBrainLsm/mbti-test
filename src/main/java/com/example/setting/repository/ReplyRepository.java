package com.example.setting.repository;

import com.example.setting.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Correct import for List

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByMbtiType(String mbtiType);
}
