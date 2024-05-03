package com.codingrecipe.member.dto.order;

import com.codingrecipe.member.entity.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderItemsResDTO {
    private long id;            // orderItem의 pk값
    private int orderPrice;     // 상품의 가격
    private int count;          // 몇개를 시켰는지
    private String productName; // 상품의 이름
    private long productId;     // product의 pk값

}
