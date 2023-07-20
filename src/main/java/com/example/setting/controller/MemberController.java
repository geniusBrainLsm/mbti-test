package com.example.setting.controller;

import com.example.setting.dto.MemberDTO;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final  MemberService memberService;
    // 회원가입 페이지 출력 요청
    @GetMapping("/setting/join")
    public String settingForm() {
        return "join";
    }
    @PostMapping("/setting/join")
    public String join(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.join");
        System.out.println("memberDTO = " + memberDTO);

        memberService.join(memberDTO);
        return "index";
    }

    @GetMapping("/setting/login")
    public String loginForm() {
        return "login";
    }
    @PostMapping("/setting/login") // session : 로그인 유지
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            System.out.println("로그인성공");
            session.setAttribute("loggedNickname", loginResult.getMemberNickname());
            return "index";
        } else {
            // login 실패
            System.out.println("로그인실패");
            return "login";
        }
    }
}
//MemberController.class