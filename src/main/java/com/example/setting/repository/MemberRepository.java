package com.example.setting.repository;

import com.example.setting.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 첫번째 인자 : 어떤 Entity인지, 두번째 인자 : pk 어떤 타입인지
public interface MemberRepository extends JpaRepository<MemberEntity, Long>
{
    //select *from member_table where member_email 이랑 같음
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
//MemberRepository.interface