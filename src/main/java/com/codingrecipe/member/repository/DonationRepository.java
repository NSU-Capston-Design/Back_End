package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DonationRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findAllByUserId(String userId);
    List<DonationEntity> findAllByDonationDate(LocalDate donationDate);
    List<DonationEntity> findAllByAmount(int amount);


}
