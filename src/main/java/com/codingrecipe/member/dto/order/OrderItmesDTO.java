package com.codingrecipe.member.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderItmesDTO {
    private String userId;
    private List<OrderRequestDTO> orderRequestDTOS;
}
