package com.codingrecipe.member.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "donation_table")
@Data
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "donation_date")
    private LocalDate donationDate;
}
