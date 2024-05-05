package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.Donation.DonationDTO;
import com.codingrecipe.member.entity.DonationEntity;
import com.codingrecipe.member.exception.NotFoundMemberException;
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
        try {

            donationDTO.setDonationDate(LocalDate.now());
            String result = donationService.donate(donationDTO);
            return ResponseEntity.ok(result);

        } catch (NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 사용자별 기부금액 총합 조회
    @GetMapping("/donations/total")
    public ResponseEntity<?> getTotalDonationAmount(@RequestParam("userId") String userId) {
        try {
            int totalDonationAmount = donationService.getTotalDonationAmount(userId);
            return ResponseEntity.ok(totalDonationAmount);
        } catch (NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

//    // 기부 날짜별 기부 내역 조회
//    @GetMapping("/donations/by-date")
//    public ResponseEntity<List<DonationDTO>> getDonationsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<DonationEntity> donationEntities = donationService.getDonationsByDate(date);
//        List<DonationDTO> donationDTOs = donationEntities.stream()
//                .map(donationEntity -> {
//                    DonationDTO donationDTO = new DonationDTO();
//                    donationDTO.setUserId(donationEntity.getUserId());
//                    donationDTO.setAmount(donationEntity.getAmount());
//                    donationDTO.setDonationDate(donationEntity.getDonationDate());
//                    return donationDTO;
//                })
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(donationDTOs);
//    }

    // 아이디별 기부 내역 조회
    @GetMapping("/donations/by-user")
    public ResponseEntity<?> getDonationsByUser(@RequestParam("userId") String userId) {
        try {
            List<DonationEntity> donationEntities = donationService.getDonationsByUser(userId);
            List<DonationDTO> donationDTOs = donationEntities.stream()
                    .map(donationEntity -> {
                        DonationDTO donationDTO = new DonationDTO();
                        donationDTO.setUserId(donationEntity.getMember(). getUserId());
                        donationDTO.setAmount(donationEntity.getAmount());
                        donationDTO.setDonationDate(donationEntity.getDonationDate());
                        return donationDTO;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(donationDTOs);
        } catch (NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 아이디별 기부 여부 확인
    @GetMapping("/donations/check")
    public ResponseEntity<?> checkDonationStatus(@RequestParam("userId") String userId) {
        try {
            boolean hasDonated = donationService.hasDonated(userId);
            return ResponseEntity.ok(hasDonated);
        } catch (NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
