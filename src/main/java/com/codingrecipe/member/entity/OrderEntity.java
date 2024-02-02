package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.dto.ProductDTO;
import com.codingrecipe.member.dto.OrderDTO;
import lombok.*;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_id")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrderId;        // 주문코드아이디

    @Column
    private LocalDateTime OrderData;        // 주문일자

    @Column
    private String OrderStatus;         // 주문상태

    @Column
    private String OrderPaymentStatus;      // 결제상태

    @Column
    private String OrderCsStatus;          // CS상태

    @Column
    private String OrderProductAmount;      // 상품금액

    @Column
    private String OrderShippingCost;       // 배송비

    @Column
    private String OrderTotalAmount;        // 총 주문금액

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JoinColumn(name = "uploaded_file")
    private ProductEntity productEntity;
    private MemberEntity memberEntity;

    @Transactional
    public void createOrder(OrderDTO orderDTO) {        //DTO를 받아 Entity에 저장하는 메서드

        this.OrderData = orderDTO.getOrderData();
        this.OrderStatus = orderDTO.getOrderStatus();
        this.OrderPaymentStatus = orderDTO.getOrderPaymentStatus();
        this.OrderCsStatus = orderDTO.OrderCsStatus();
        this.OrderProductAmount = orderDTO.OrderProductAmount();
        this.OrderShippingCost = orderDTO.OrderShippingCost();
        this.OrderTotalAmount = orderDTO.OrderTotalAmount();
    }



}
