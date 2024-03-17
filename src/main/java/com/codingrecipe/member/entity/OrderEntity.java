package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.order.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;                       // 주문아이디

    @Column
    private LocalDateTime orderDate;            // 주문날짜

    @Column
    private String orderStatus;                 // 주문상태

    @Column
    private String orderPaymentStatus;          // 결제상태

    @Column
    private int productPrice;                   // 상품가격

    @Column
    private String productName;                 // 상품이름

    @Column
    private int orderShippingCost;              // 배송비

    @Column
    private int orderTotalCost;                 // 총 주문금액



    @Transactional
    public void createOrder(OrderDTO orderDTO){ //DTO를 받아 Entity에 저장하는 메서드

        this.orderDate = orderDTO.getOrderDate();
        this.orderStatus = orderDTO.getOrderStatus();
        this.orderPaymentStatus = orderDTO.getOrderPaymentStatus();
        this.productPrice = orderDTO.getProductPrice();
        this.productName = orderDTO.getProductName();
        this.orderShippingCost = orderDTO.getOrderShippingCost();
        this.orderTotalCost = orderDTO.getOrderTotalCost();
    }

    // Getters and setters
}
