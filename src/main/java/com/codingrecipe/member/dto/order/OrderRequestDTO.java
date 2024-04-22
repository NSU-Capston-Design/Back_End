package com.codingrecipe.member.dto.order;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private long memberId;
    private int[] count;
    private int[] price;
    private long[] productId;

}
