package com.example.setting.controller;

import com.example.setting.dto.MemberDTO;
import com.example.setting.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;

import java.util.NoSuchElementException;

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
            session.setAttribute("loggedEmail", loginResult.getMemberEmail());

            return "redirect:/setting/board";
gi
        } else {
            // login 실패
            model.addAttribute("errorMessage", "로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }
    @GetMapping("/setting/board/login")
    public String BoardloginForm()
    {
        return "login";
    }
    @PostMapping("/setting/board/login")
    public String Boardlogin(@ModelAttribute MemberDTO memberDTO, HttpSession session, ModelMap model) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            System.out.println("로그인 성공");
            session.setAttribute("loggedNickname", loginResult.getMemberNickname());
            return "notice_board";

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

    @PostMapping("/setting/mypage")  //마이페이지
    public String updateEmail(@RequestParam String newEmail, HttpServletRequest request, Model model) {
        // 세션에서 현재 로그인한 유저의 정보(예: email) 가져오기
        String currentEmail = (String) request.getSession().getAttribute("email");

        if (memberService.isMemberExists(newEmail)) {
            model.addAttribute("errorMessage", "이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요.");
            return "redirect:/setting/mypage"; //이거 어캐하는지 모르겠어요

        }

        // MemberDTO 객체 생성 및 회원 정보 업데이트 로직 실행
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(newEmail);

        memberService.updateInfo(memberDTO, request);

        return "redirect:/setting/mypage";  // 업데이트 후 리다이렉션 할 페이지
    }

    @PostMapping("/setting/mypage/changePassword")
    public String changePassword(@RequestParam("newPassword") String newPassword, HttpServletRequest request, Model model) {
        // 세션에서 현재 로그인한 유저의 정보(예: email) 가져오기
        String currentEmail = (String) request.getSession().getAttribute("loggedEmail");

        // MemberDTO 객체 생성 및 새로운 비밀번호 설정
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(currentEmail);
        memberDTO.setMemberPassword(newPassword);

        // 비밀번호 변경 서비스 호출
        try {
            memberService.changePassword(memberDTO, request);
            model.addAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");

            return "redirect:/setting/mypage";  // 업데이트 후 리다이렉션 할 페이지

        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "비밀번호 변경에 실패하였습니다. 다시 시도해주세요.");

            return "redirect:/setting/mypage";  // 실패 시 리다이렉션 할 페이지
        }
    }

    @PostMapping("/setting/mypage/deleteAccount")
    public String deleteAccount(HttpServletRequest request) {
        memberService.deleteMember(request);

        return "redirect:/";  // 삭제 후 리다이렉션 할 페이지 (예: 메인 페이지)
    }


//MemberController.class
}