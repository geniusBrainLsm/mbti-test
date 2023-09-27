// UserSecurityService.java

package com.example.setting.service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.setting.MemberRole;
import com.example.setting.entity.MemberEntity;
import com.example.setting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Optional<MemberEntity> _memberEntity = this.memberRepository.findByMemberEmail(memberEmail);
        if (_memberEntity.isEmpty()) {
            System.out.println("사용자를 찾을 수 없습니다: " + memberEmail);  // 여기에 로깅 추가
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        MemberEntity memberEntity = _memberEntity.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(memberEmail)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }

        System.out.println("불러온 사용자 정보: " + memberEntity);  // 여기에 로깅 추가
        System.out.println("비번" + memberEntity.getMemberPassword());
        return new User(memberEntity.getMemberEmail(), memberEntity.getMemberPassword(), authorities);
    }

}
