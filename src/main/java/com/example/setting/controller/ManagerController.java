package com.example.setting.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.setting.entity.MemberEntity;
import com.example.setting.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
@Controller
public class ManagerController {
    private final MemberService memberService;
    @Autowired
    public ManagerController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/admin/list")
    public String index(Model model) {
        List<MemberEntity> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "admin";
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        // 회원을 삭제하고 삭제가 성공하면 HttpStatus.OK를 반환
        // 삭제가 실패하면 HttpStatus.INTERNAL_SERVER_ERROR 또는 다른 적절한 상태 코드를 반환
       memberService.deleteMember(id);
       return new ResponseEntity<>("삭제됨", HttpStatus.OK);
    }
}

