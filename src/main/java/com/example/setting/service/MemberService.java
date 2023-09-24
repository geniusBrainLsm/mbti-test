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

//    public MemberDTO login(MemberDTO memberDTO) {
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
//        if (byMemberEmail.isPresent()) {
//            MemberEntity memberEntity = byMemberEmail.get();
//            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
//                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//                System.out.println("1");
//                return dto;
//            } else {
//                System.out.println("2");
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
//
//    public MemberDTO updateInfo(MemberDTO memberDTO, HttpServletRequest request) {
//        // 세션에서 이메일 가져오기
//        String sessionEmail = (String) request.getSession().getAttribute("loggedEmail");
//
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(sessionEmail);
//        if(byMemberEmail.isPresent()){
//            MemberEntity memberEntity = byMemberEmail.get();
//            memberEntity.setMemberEmail(memberDTO.getMemberEmail());
//
//            MemberEntity updatedEntity = memberRepository.save(memberEntity);
//            request.getSession().setAttribute("loggedEmail", memberDTO.getMemberEmail());
//
//            return MemberDTO.toMemberDTO(updatedEntity);
//        } else {
//            throw new NoSuchElementException("이메일을 찾을 수 없습니다: " + sessionEmail);
//        }
//    }
//
//    public MemberDTO changePassword(MemberDTO memberDTO, HttpServletRequest request) {
//        // 세션에서 이메일 가져오기
//        String sessionEmail = (String) request.getSession().getAttribute("loggedEmail");
//
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(sessionEmail);
//        if (byMemberEmail.isPresent()) {
//            MemberEntity memberEntity = byMemberEmail.get();
//
//            // 새로운 비밀번호로 변경
//            memberEntity.setMemberPassword(memberDTO.getMemberPassword());
//
//            MemberEntity updatedEntity = memberRepository.save(memberEntity);
//
//            return MemberDTO.toMemberDTO(updatedEntity);
//        } else {
//            throw new NoSuchElementException("이메일을 찾을 수 없습니다: " + sessionEmail);
//        }
//    }
//    @Transactional
//    public void deleteMember(HttpServletRequest request) {
//        String sessionEmail = (String) request.getSession().getAttribute("loggedEmail");
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(sessionEmail);
//
//        if (byMemberEmail.isPresent()) {
//            memberRepository.delete(byMemberEmail.get());
//            request.getSession().invalidate();  // 세션 무효화
//        } else {
//            throw new NoSuchElementException("이메일을 찾을 수 없습니다: " + sessionEmail);
//        }
//    }
//
//

//
//    public MemberDTO findByNickname(String nickname) {
//        MemberEntity entity = memberRepository.findByMemberNickname(nickname);
//        return entity != null ? MemberDTO.toMemberDTO(entity) : null;
//    }

    // 암호화된 비밀번호 생성


    public boolean isNicknameExists(String nickname) {
        return memberRepository.existsByMemberNickname(nickname);
    }

    public boolean isMemberExists(String memberEmail) {
        return memberRepository.existsByMemberEmail(memberEmail);
    }


}
