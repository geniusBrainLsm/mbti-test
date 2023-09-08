package com.example.setting.service;

import com.example.setting.dto.MemberDTO;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(MemberDTO memberDTO) {

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                System.out.println("1");
                return dto;
            } else {
                System.out.println("2");
                return null;
            }
        } else {
            return null;
        }
    }

    public MemberDTO findByNickname(String nickname) {
        MemberEntity entity = memberRepository.findByMemberNickname(nickname);
        return entity != null ? MemberDTO.toMemberDTO(entity) : null;
    }



    public boolean isNicknameExists(String nickname) {
        return memberRepository.existsByMemberNickname(nickname);
    }

    public boolean isMemberExists(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }

}
