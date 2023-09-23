package com.example.setting.repository;

import com.example.setting.dto.MemberDTO;
import com.example.setting.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    boolean existsByMemberEmail(String memberEmail);

    boolean existsByMemberNickname(String memberNickname);

    MemberEntity findByMemberNickname(String memberNickname);
}
