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


        // 아이디 중복 체크
        if (isMemberExists(memberDTO.getMemberEmail())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요.");
        }

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }



    public boolean isNicknameExists(String nickname) {
        return memberRepository.existsByMemberNickname(nickname);
    }

    public boolean isNicknameLengthValid(String nickname) {
        return nickname.length() >= 3 && nickname.length() <= 20;
    }

    public boolean isMemberExists(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }
    public boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        // 간단한 이메일 형식 체크 (실제로는 더 정교한 검사가 필요할 수 있음)
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    public boolean isPasswordLengthValid(String password) {
        return password != null && password.length() >= 8 && password.length() <= 50;
    }
}
