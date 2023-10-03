package com.example.setting.service;

import com.example.setting.dto.MemberDTO;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberEntity create(MemberDTO memberDto) {
        // 암호화된 비밀번호 생성
        String encryptedPassword = passwordEncoder.encode(memberDto.getMemberPassword());

        // 회원 엔티티 생성 및 저장
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberMbti(memberDto.getMemberMbti()); //mbti 타입추가
        memberEntity.setMemberNickname(memberDto.getMemberNickname());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(encryptedPassword);

        return this.memberRepository.save(memberEntity);
    }

    public void delete(String memberEmail){
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        memberRepository.delete(memberEntity);
    }

    public void findPw(String memberNickaname, String memberEmail){

    }


    public boolean isNicknameExists(String nickname) {
        return memberRepository.existsByMemberNickname(nickname);
    }

    public boolean isMemberExists(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }


}
