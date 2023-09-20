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

        // 아이디 중복 체크
        if (memberService.isMemberExists(memberDTO.getMemberEmail())) {
            model.addAttribute("errorMessage", "이미 존재하는 이메일입니다. 다른 아이디를 사용해주세요.");
            return "join";
        }


        memberService.join(memberDTO);
        return "notice_board";
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
            return "redirect:/setting/board";
        } else {
            // login 실패
            model.addAttribute("errorMessage", "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }



    @GetMapping("/setting/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화하여 로그아웃 처리
        return "redirect:/setting/board";
    }
}
//MemberController.class