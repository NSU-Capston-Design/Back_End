package com.codingrecipe.member.dto.member;

import com.codingrecipe.member.entity.MemberEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long memberId;
    private String userId;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userPhone;
    private String userBirth;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(memberEntity.getMemberId());
        memberDTO.setUserId(memberEntity.getUserId());
        memberDTO.setUserEmail(memberEntity.getUserEmail());
        memberDTO.setUserPassword(memberEntity.getUserPassword());
        memberDTO.setUserName(memberEntity.getUserName());
        memberDTO.setUserPhone(memberEntity.getUserPhone()); // memberPhone 설정
        memberDTO.setUserBirth(memberEntity.getUserBirth()); // memberBirth 설정
        return memberDTO;
    }
}
