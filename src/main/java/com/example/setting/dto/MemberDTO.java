package com.example.setting.dto;

import com.example.setting.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//lombok dependency추가
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO { //회원 정보를 필드로 정의
    private Long id;
    private String memberMbti;
    private String memberNickname;
    private String memberEmail;
    private String memberPassword;


    //lombok 어노테이션으로 getter,setter,생성자,toString 메서드 생략 가능

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberMbti(memberEntity.getMemberMbti());
        memberDTO.setMemberNickname(memberEntity.getMemberNickname());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());

        return memberDTO;
    }


}
//MemberDTO.java
//package com.example.setting.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class MemberDTO {
//
//    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
//    @Size(min = 3, max = 25)
//    public String memberNickname;
//
//    @NotEmpty(message = "비밀번호는 필수항목입니다.")
//    private String memberPassword1;
//
//    @NotEmpty(message = "비밀번호 중복확인은 필수항목입니다.")
//    private String memberPassword2;
//
//    @NotEmpty(message = "사용자 Email은 필수항목입니다.")
//    @Email
//    private String memberEmail;
//
//    @Builder
//    public MemberDTO(String memberNickname, String memberEmail, String memberPassword1){
//        this.memberNickname = memberNickname;
//        this.memberEmail = memberEmail;
//        this.memberPassword1 = memberPassword1;
//    }
//}
