package com.example.setting.controller;

import com.example.setting.dto.MailDTO;
import com.example.setting.dto.MemberDTO;
import com.example.setting.Form.JoinForm;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import com.example.setting.service.MailService;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final  MemberService memberService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    @GetMapping("/member/join")
    public String join(JoinForm joinform) {
        return "join";
    }

    @PostMapping("/member/join")
    public String join(@Valid JoinForm joinform, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "join";
        }
        if(!joinform.getMemberPassword1().equals(joinform.getMemberPassword2())){
            bindingResult.rejectValue("memberPassword2","passwordInCorrect", "비밀번호 중복이 일치하지 않습니다");
            return "join";
        }

        try {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setMemberMbti(joinform.getMemberMbti());
            memberDTO.setMemberNickname(joinform.getMemberNickname());
            memberDTO.setMemberEmail(joinform.getMemberEmail());
            memberDTO.setMemberPassword(joinform.getMemberPassword1());

            memberService.create(memberDTO);
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "join";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "join";
        }

        return "redirect:/notice_board";
    }

    @GetMapping("/member/login")
    public String login()
    {
        return "login";
    }


    @GetMapping("/member/findPw")
    public String findPw()
    {
        return "findPw";
    }

    @GetMapping("/member/findmail")
    public String findmail()
    {
        return "findmail";
    }

    @ResponseBody
    @PostMapping("findmail")
    public String findEmail(@RequestParam("memberNickname") String memberNickname, @RequestParam("memberPassword") String memberPassword){
        boolean isMemberExist = memberService.isNicknameExists(memberNickname);
        if(isMemberExist == true){
            MemberEntity member = memberRepository.findByMemberNickname(memberNickname);
            String realPassword = member.getMemberPassword();
            //맴버패스워드는 입력한 값, 리얼패스워드는 db 패스워드
            if(passwordEncoder.matches(memberPassword, realPassword)){
                System.out.println(member.getMemberEmail());
                return member.getMemberEmail();
            }
            else{
                return "false";
            }
        }
        else{
            return "false";
        }
    }


}