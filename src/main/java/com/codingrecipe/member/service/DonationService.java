package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.Donation.DonationDTO;
import com.codingrecipe.member.entity.DonationEntity;
import com.codingrecipe.member.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public String donate(DonationDTO donationDTO) {
        DonationEntity donationEntity = new DonationEntity();
        donationEntity.setUserId(donationDTO.getUserId());
        donationEntity.setAmount(donationDTO.getAmount());
        donationEntity.setDonationDate(LocalDate.now());

        donationRepository.save(donationEntity);

        return "기부가 완료되었습니다.";
    }

    public int getTotalDonationAmount(String userId) {
        List<DonationEntity> donations = donationRepository.findAllByUserId(userId);
        int totalAmount = 0;
        for (DonationEntity donation : donations) {
            totalAmount += donation.getAmount();
        }
        return totalAmount;
    }

    public List<String> getTopDonators(int limit) {
        List<DonationEntity> donations = donationRepository.findAll();

        List<String> topDonators = donations.stream()
                .collect(Collectors.groupingBy(DonationEntity::getUserId, Collectors.summingInt(DonationEntity::getAmount)))
                .entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .limit(limit)
                .map(entry -> entry.getKey() + " (총 기부금액: " + entry.getValue() + "원)")
                .collect(Collectors.toList());

        return topDonators;
    }

    public String getDonationGrade(int donationAmount) {
        String grade;
        if (donationAmount >= 1000000) {
            grade = "다이아몬드";
        } else if (donationAmount >= 500000) {
            grade = "플래티넘";
        } else if (donationAmount >= 100000) {
            grade = "골드";
        } else if (donationAmount >= 50000) {
            grade = "실버";
        } else {
            grade = "브론즈";
        }
        return grade;
    }

    public List<DonationEntity> getDonationsByDate(LocalDate date) {
        return donationRepository.findAllByDonationDate(date);
    }

    public List<DonationEntity> getDonationsByAmount(int amount) {
        return donationRepository.findAllByAmount(amount);
    }
}
