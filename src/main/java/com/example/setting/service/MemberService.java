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
import java.util.List;
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

    public void deleteMember(Long memberId) {
        Optional<MemberEntity> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            MemberEntity memberEntity = memberOptional.get();
            memberRepository.delete(memberEntity);
        } else {
            throw new NoSuchElementException("맴버 ID :" + memberId + " 존재하지 않는 맴버 ID입니다");
        }
    }



    public boolean isNicknameExists(String nickname) {
        return memberRepository.existsByMemberNickname(nickname);
    }

    public boolean isMemberExists(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }

    public List<MemberEntity> getAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
