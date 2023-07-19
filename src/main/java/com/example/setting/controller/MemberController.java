package com.example.setting.controller;

import com.example.setting.dto.MemberDTO;
import com.example.setting.service.MemberService;
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

}
//MemberController.class