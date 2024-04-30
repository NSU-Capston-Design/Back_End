package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.DonationEntity;
import com.codingrecipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    @Query("SELECT d FROM DonationEntity d where d.member = :member")
    List<DonationEntity> findAllByUserId(MemberEntity member);
    List<DonationEntity> findAllByDonationDate(LocalDate donationDate);
    List<DonationEntity> findAllByAmount(int amount);

    @Query("SELECT d FROM DonationEntity d WHERE d.member = :member")
    List<DonationEntity> findAllByMember(MemberEntity member);
}
