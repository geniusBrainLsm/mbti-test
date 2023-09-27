package com.example.setting.entity;

import com.example.setting.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "member_table") //database에 해당 이름의 테이블 생성
public class MemberEntity { //table 역할
    //jpa ==> database를 객체처럼 사용 가능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String memberMbti;

    @Column(unique = true)
    private String memberNickname;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    //댓글이랑 연결
    @OneToMany(mappedBy="member", cascade=CascadeType.ALL)
    private List<Comment> comments;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberMbti(memberDTO.getMemberMbti());
        memberEntity.setMemberNickname(memberDTO.getMemberNickname());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());

        return memberEntity;
    }

}
