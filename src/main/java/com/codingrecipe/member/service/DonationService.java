package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.Donation.DonationDTO;
import com.codingrecipe.member.entity.DonationEntity;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.repository.DonationRepository;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DonationService {

    private DonationRepository donationRepository;
    private MemberRepository memberRepository;

    /**
     * 기부하기
     */
    @Transactional
    public String donate(DonationDTO donationDTO) throws NotFoundMemberException {

        log.info("===기부하기===");

        log.info("===userId로 회원 조회=== userId : " + donationDTO.getUserId());
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(donationDTO.getUserId());
        if (byUserId.isEmpty()){
            log.info("===회원 조회 실패=== userId : " + donationDTO.getUserId());
            throw new NotFoundMemberException("없는 회원입니다.");
        }
        DonationEntity donationEntity = new DonationEntity();
        donationEntity.setMember(byUserId.get());
        donationEntity.setAmount(donationDTO.getAmount());
        donationEntity.setDonationDate(LocalDate.now());

        donationRepository.save(donationEntity);

        return "기부가 완료되었습니다.";
    }

    /**
     * 기부 총액 조회
     */
    @Transactional
    public int getTotalDonationAmount(String userId) throws NotFoundMemberException {
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()){
            throw new NotFoundMemberException("없는 회원입니다.");
        }
        List<DonationEntity> allByMember = donationRepository.findAllByMember(byUserId.get());
        int totalAmount = 0;
        for (DonationEntity donation : allByMember) {
            totalAmount += donation.getAmount();
        }
        return totalAmount;
    }

    /**
     * Top 10명 기부자 닉네임 반환
     * @param limit
     * @return
     */
    @Transactional
    public List<String> getTopDonators(int limit) {
        List<DonationEntity> donations = donationRepository.findAll();

        return donations.stream()
                .collect(Collectors.groupingBy(DonationEntity::getMember, Collectors.summingInt(DonationEntity::getAmount)))
                .entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .limit(limit)
                .map(entry -> entry.getKey().getUserId())
                .collect(Collectors.toList());
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

    // 기부 여부 확인 메서드
    @Transactional
    public boolean hasDonated(String userId) throws NotFoundMemberException {
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()){
            throw new NotFoundMemberException("사용자를 찾을 수 없습니다. userId : " + userId);
        }
        // 해당 사용자의 기부 내역을 조회합니다.
        List<DonationEntity> donations = donationRepository.findAllByUserId(byUserId.get());
        return !donations.isEmpty();
        // 기부 내역이 존재하면 true를 반환합니다.
    }

    /**
     * 유저 기부내역 확인
     */
    @Transactional
    public List<DonationEntity> getDonationsByUser(String userId) throws NotFoundMemberException {
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()){
            throw new NotFoundMemberException("사용자를 찾을 수 없습니다. userId : " + userId);
        }

        return donationRepository.findAllByUserId(byUserId.get());
    }

    public List<DonationEntity> getDonationsByDate(LocalDate date) {
        return donationRepository.findAllByDonationDate(date);
    }

    public List<DonationEntity> getDonationsByAmount(int amount) {
        return donationRepository.findAllByAmount(amount);
    }

}
