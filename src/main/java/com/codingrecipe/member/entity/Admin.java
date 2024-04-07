package com.codingrecipe.member.entity;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Admin {

    /**
     * 어드민 계정은 id가 인덱스, 비밀번호는 랜덤생성예정
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;    // admin id

    @Column(name = "admin_pw")
    private String adminPw; // admin pw
}
