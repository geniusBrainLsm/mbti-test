package com.example.setting.controller;

import com.example.setting.dto.MemberDTO;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final  MemberService memberService;

    // 중복 확인을 수행하는 컨트롤러 메서드
    @GetMapping("/setting/join")
    public String settingForm()
    {
        return "join";
    }

    @PostMapping("/setting/join")
    public String join(@ModelAttribute MemberDTO memberDTO, Model model) {
        System.out.println("MemberController.join");
        System.out.println("memberDTO = " + memberDTO);

        // 닉네임 중복 체크
        if (memberService.isNicknameExists(memberDTO.getMemberNickname())) {
            model.addAttribute("errorMessage", "이미 존재하는 닉네임입니다. 다른 닉네임을 사용해주세요.");
            return "join";
        }

        // 닉네임 길이 검사
        if (!memberService.isNicknameLengthValid(memberDTO.getMemberNickname())) {
            model.addAttribute("errorMessage", "닉네임은 3자 이상 20자 이내로 입력해주세요.");
            return "join";
        }

        // 아이디 중복 체크
        if (memberService.isMemberExists(memberDTO.getMemberEmail())) {
            model.addAttribute("errorMessage", "이미 존재하는 이메일입니다. 다른 아이디를 사용해주세요.");
            return "join";
        }

        //이메일 유효성
        if (!memberService.isEmailValid(memberDTO.getMemberEmail())) {
            model.addAttribute("errorMessage", "유효하지 않은 이메일 형식입니다.");
            model.addAttribute("memberDTO", memberDTO);
            return "join";
        }

        //비밀번호 유효성
        if (!memberService.isPasswordLengthValid(memberDTO.getMemberPassword())) {
            model.addAttribute("errorMessage", "비밀번호는 8자 이상 50자 이내로 입력해주세요.");
            model.addAttribute("memberDTO", memberDTO);
            return "join";
        }

        memberService.join(memberDTO);
        return "index";
    }

    @GetMapping("/setting/login")
    public String loginForm()
    {
        return "login";
    }
    @PostMapping("/setting/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, ModelMap model) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            System.out.println("로그인 성공");
            session.setAttribute("loggedNickname", loginResult.getMemberNickname());
            return "index";
        } else {
            // login 실패
            model.addAttribute("errorMessage", "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }



    @GetMapping("/setting/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화하여 로그아웃 처리
        return "index";
    }
}
//MemberController.class