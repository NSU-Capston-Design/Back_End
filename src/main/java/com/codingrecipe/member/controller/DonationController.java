package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.Donation.DonationDTO;
import com.codingrecipe.member.entity.DonationEntity;
import com.codingrecipe.member.service.DonationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    // 기부하기
    @PostMapping("/donate")
    public ResponseEntity<String> donate(@RequestBody DonationDTO donationDTO) {
        donationDTO.setDonationDate(LocalDate.now());
        String result = donationService.donate(donationDTO);
        if (result != null) {
            return ResponseEntity.ok("기부가 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // 사용자별 기부금액 총합 조회
    @GetMapping("/donations/total")
    public ResponseEntity<Integer> getTotalDonationAmount(@RequestParam("userId") String userId) {
        int totalDonationAmount = donationService.getTotalDonationAmount(userId);
        return ResponseEntity.ok(totalDonationAmount);
    }

    // 상위 기부자 10명 랭킹 조회
    @GetMapping("/donations/top")
    public ResponseEntity<List<String>> getTopDonators() {
        List<String> topDonators = donationService.getTopDonators(10);
        return ResponseEntity.ok(topDonators);
    }

    // 기부금액 별 기부등급 조회
    @GetMapping("/donations/grade")
    public ResponseEntity<String> getDonationGrade(@RequestParam("donationAmount") int donationAmount) {
        String donationGrade = donationService.getDonationGrade(donationAmount);
        return ResponseEntity.ok(donationGrade);
    }

    // 기부 날짜별 기부 내역 조회
    @GetMapping("/donations/by-date")
    public ResponseEntity<List<DonationDTO>> getDonationsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<DonationEntity> donationEntities = donationService.getDonationsByDate(date);
        List<DonationDTO> donationDTOs = donationEntities.stream()
                .map(donationEntity -> {
                    DonationDTO donationDTO = new DonationDTO();
                    donationDTO.setUserId(donationEntity.getUserId());
                    donationDTO.setAmount(donationEntity.getAmount());
                    donationDTO.setDonationDate(donationEntity.getDonationDate());
                    return donationDTO;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(donationDTOs);
    }
}