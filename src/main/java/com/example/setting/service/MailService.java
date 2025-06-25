package com.example.setting.service;

import com.example.setting.dto.MailDTO;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Value("${stmp.mail}")
    String senderEmail;


    private static int number;

    public static void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        if (memberService.isMemberExists(mail)) {  // 이메일 중복 확인
            throw new IllegalArgumentException("이미 등록된 사용자입니다.");  // 예외 발생
        }
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);

        return number;
    }

    // 임시 비밀번호 생성
    public static String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    public MailDTO createMailAndChangePassword(String memberEmail) {
        String str = getTempPassword();
        MailDTO dto = new MailDTO();
        dto.setAddress(memberEmail);
        dto.setTitle("MBTI_test 임시비밀번호입니다.");
        dto.setMessage("회원님의 임시 비밀번호는 "
                + str + " 입니다." + "로그인 후에 비밀번호를 변경해주세요!");
        updatePassword(str, memberEmail);
        return dto;
    }

    // MailDto를 바탕으로 실제 이메일 전송
    public boolean mailSend(MailDTO mailDTO) {
        try {
            System.out.println("전송 완료!");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailDTO.getAddress());
            message.setSubject(mailDTO.getTitle());
            message.setText(mailDTO.getMessage());
            message.setFrom("geniusbrainlsm@gmail.com");
            message.setReplyTo("geniusbrainlsm@gmail.com");

            System.out.println("message"+message);

            javaMailSender.send(message);

            return true;  // 메일 전송 성공
        } catch (MailException e) {
            e.printStackTrace();
            return false;  // 메일 전송 실패
        }
    }

    //임시 비밀번호로 업데이트
    public boolean updatePassword(String str, String email){
        try {
            Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(email);

            if (optionalMember.isPresent()) {
                MemberEntity member = optionalMember.get();
                member.setMemberPassword(passwordEncoder.encode(str));
                memberRepository.save(member);
                return true;
            } else {
                // email에 해당하는 회원이 없음
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


}