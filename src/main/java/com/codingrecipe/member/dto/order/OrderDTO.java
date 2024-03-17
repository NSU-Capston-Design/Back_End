package com.codingrecipe.member.dto.order;

import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.OrderEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private String orderStatus;
    private String orderPaymentStatus;
    private int productPrice;
    private String productName;
    private int orderShippingCost;
    private int orderTotalCost;

    private MemberEntity memberEntity;

    public OrderDTO(LocalDateTime orderDate, String orderStatus, String orderPaymentStatus,
                    int productPrice, String productName, int orderShippingCost,
                    int orderTotalCost, MemberEntity memberEntity){   // 필드 초기화
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderPaymentStatus = orderPaymentStatus;
        this.productPrice = productPrice;
        this.productName = productName;
        this.orderShippingCost = orderShippingCost;
        this.orderTotalCost = orderTotalCost;

        this.memberEntity = memberEntity;
    }

    public OrderDTO(OrderEntity orderEntity){   // 엔티티를 DTO로 변환
        this.orderId = orderEntity.getOrderId();
        this.orderDate = orderEntity.getOrderDate();
        this.orderStatus = orderEntity.getOrderStatus();
        this.orderPaymentStatus = orderEntity.getOrderPaymentStatus();
        this.productPrice = orderEntity.getProductPrice();
        this.productName = orderEntity.getProductName();
        this.orderShippingCost = orderEntity.getOrderShippingCost();
        this.orderTotalCost = orderEntity.getOrderTotalCost();
    }

    // Getters and setters
}