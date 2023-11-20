package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    @Column // 새로운 필드
    private String memberPhone;

    @Column // 새로운 필드
    private String memberBirth;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone()); // memberPhone 설정
        memberEntity.setMemberBirth(memberDTO.getMemberBirth()); // memberBirth 설정
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone()); // memberPhone 설정
        memberEntity.setMemberBirth(memberDTO.getMemberBirth()); // memberBirth 설정
        return memberEntity;
    }
}
