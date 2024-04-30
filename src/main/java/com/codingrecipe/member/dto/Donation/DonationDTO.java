package com.codingrecipe.member.dto.Donation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DonationDTO {

    private String userId;
    private int amount;
    private LocalDate donationDate;
}
