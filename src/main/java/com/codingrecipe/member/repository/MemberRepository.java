package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일로 회원 정보 조회 (select * from member_table where member_email=?)

    /*Optional<MemberEntity> findByMemberEmail(String memberEmail);*/


    // 유저 아이디로 회원 정보 조회
    @Query(value = "select m from MemberEntity m where m.userId = :userId")
    Optional<MemberEntity> findByUserId(String userId);
}