package com.example.setting;

import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class SettingApplicationTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindByMemberEmail() {
        String email = "qwe@qwe.qwe";  // 실제 데이터베이스에 존재하는 이메일 주소로 변경하세요.

        Optional<MemberEntity> result = memberRepository.findByMemberEmail(email);

        assert(result.isPresent());
        System.out.println("Found user: " + result.get().getMemberNickname());
    }

}
