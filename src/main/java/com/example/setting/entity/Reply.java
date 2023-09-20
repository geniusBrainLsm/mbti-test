package com.example.setting.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "replies") //database에 해당 이름의 테이블 생성
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mbtiType;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createdAt;
}
