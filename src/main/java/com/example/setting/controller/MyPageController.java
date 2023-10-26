package com.example.setting.controller;

import com.example.setting.MemberRole;
import com.example.setting.dto.MemberDTO;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @GetMapping("/mypage")
    public String myPage(Model model, Principal principal) {
        String username = principal.getName();  // 이메일로 조회

        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        model.addAttribute("memberEntity", member);

        return "mypage";
    }
    @PostMapping("/mypage/editNickname")
    public String editInfoNickname(@RequestParam("newNickname") String newNickname, Principal principal){

        String username = principal.getName();

        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        member.setMemberNickname(newNickname);


        memberRepository.save(member);

        return "redirect:/mypage";
    }
    @PostMapping("/mypage/editEmail")
    public String editInfoEmail(@RequestParam("newEmail") String newEmail, Principal principal){

        String username = principal.getName();

        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        member.setMemberEmail(newEmail);


        memberRepository.save(member);

        return "redirect:/mypage";
    }

    private final PasswordEncoder passwordEncoder;
    @PostMapping("/mypage/changePassword")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editPassword(@RequestParam("currentPassword") String currentPassword,
                               @RequestParam("newPassword") String newPassword,
                               Principal principal) {

        String username = principal.getName();
        MemberEntity member = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Map<String, Object> response = new HashMap<>(); //ajax에서 쓸 isMatchCurrentPassword를 json으로 넘기는데 json이 key-value 형식이라서 이거써요

        if (!passwordEncoder.matches(currentPassword, member.getMemberPassword())) {
            response.put("isMatchCurrentPassword", false);
        }
        else{
            response.put("isMatchCurrentPassword", true);
            member.setMemberPassword(passwordEncoder.encode(newPassword));

            memberRepository.save(member);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/mypage/deleteMember")
    public String deleteMember(Principal principal){
        String username = principal.getName();
        memberService.delete(username);
        return "redirect:/";

    }
}