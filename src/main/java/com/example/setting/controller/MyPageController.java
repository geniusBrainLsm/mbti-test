package com.example.setting.controller;

import com.example.setting.dto.MemberDTO;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/setting/mypage")
    public String mypage(Model model, HttpSession session) {
        String loggedNickname = (String) session.getAttribute("loggedNickname");

        if (loggedNickname == null) {
            // 로그인하지 않은 상태일 때의 처리
            return "redirect:/setting/login";
        }

        MemberDTO memberDTO = memberService.findByNickname(loggedNickname);

        if (memberDTO == null) {
            // 멤버가 존재하지 않는 경우에 대한 처리
            return "redirect:/setting/login";
        }

        model.addAttribute("memberEntity", memberDTO);

        return "mypage";
    }
}
