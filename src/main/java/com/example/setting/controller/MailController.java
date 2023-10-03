package com.example.setting.controller;

import com.example.setting.dto.MailDTO;
import com.example.setting.dto.MemberDTO;
import com.example.setting.service.MailService;
import com.example.setting.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/checkEmail")
    public String checkEmail(@RequestParam String email){
        boolean exists = memberService.isMemberExists(email);
        if (exists) {
            return "duplicated";
        } else {
            return "ok";
        }
    }
    @ResponseBody
    @PostMapping("/sendMail")
    public String sendMail(@RequestParam String email){
        int number = mailService.sendMail(email);
        return Integer.toString(number);
    }
    // 비밀번호 찾기시, 임시 비밀번호 담긴 이메일 보내기
    @Transactional
    @ResponseBody
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("memberNickname") String memberNickname,@RequestParam("memberEmail") String memberEmail){
        MailDTO dto = mailService.createMailAndChangePassword(memberEmail);
        boolean isMemberExist = memberService.isNicknameExists(memberNickname);
        if (dto == null || isMemberExist == false){
            return "false";
        }
        boolean sended = mailService.mailSend(dto);
        if (!sended) {
            // 메일 전송 실패
            return "false";
        }
        return "/member/login";
    }

}