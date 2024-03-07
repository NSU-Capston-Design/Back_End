package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.member.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String userEmail;

    @Column
    private String userPassword;

    @Column
    private String userName;

    @Column(unique = true)
    private String userPhone;

    @Column // 새로운 필드
    private String userBirth;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL)
    private List<ProductEntity> productEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setUserEmail(memberDTO.getUserEmail());
        memberEntity.setUserPassword(memberDTO.getUserPassword());
        memberEntity.setUserName(memberDTO.getUserName());
        memberEntity.setUserPhone(memberDTO.getUserPhone()); // memberPhone 설정
        memberEntity.setUserBirth(memberDTO.getUserBirth()); // memberBirth 설정
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setUserEmail(memberDTO.getUserEmail());
        memberEntity.setUserPassword(memberDTO.getUserPassword());
        memberEntity.setUserName(memberDTO.getUserName());
        memberEntity.setUserPhone(memberDTO.getUserPhone()); // memberPhone 설정
        memberEntity.setUserBirth(memberDTO.getUserBirth()); // memberBirth 설정
        return memberEntity;
    }
}
