package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.entity.enums.DeliveryStatus;
import com.codingrecipe.member.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;                       // 주문아이디

    @Column
    private LocalDateTime orderDate;            // 주문날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;            // 주문상태

    @Enumerated(EnumType.STRING)
    private DeliveryStatus delivery;      // 배송 상태 (BEFO, SHIP, COMP)

    @Column
    private String orderPaymentStatus;          // 결제상태

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column
    private int orderTotalCost;                 // 총 주문금액


    //==연관관계 메서드==//
    public void setMember(MemberEntity member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성 메서드==//

    /**
     * 주문 생성
     */
    public static OrderEntity createOrder(MemberEntity member, OrderItem... orderItems){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setMember(member);
        for (OrderItem orderItem : orderItems){
            orderEntity.addOrderItem(orderItem);
        }
        orderEntity.setOrderStatus(OrderStatus.ORDER);
        orderEntity.setOrderDate(LocalDateTime.now());
        return orderEntity;
    }

    //==비즈니스 로직==//

    /**
     * 주문 취소
     */
    public void cancel(){
        if (getDelivery() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


//    @Transactional
//    public void createOrder(OrderDTO orderDTO){ //DTO를 받아 Entity에 저장하는 메서드
//
//        this.orderDate = orderDTO.getOrderDate();
//        this.orderStatus = orderDTO.getOrderStatus();
//        this.orderPaymentStatus = orderDTO.getOrderPaymentStatus();
//        this.productPrice = orderDTO.getProductPrice();
//        this.productName = orderDTO.getProductName();
//        this.orderShippingCost = orderDTO.getOrderShippingCost();
//        this.orderTotalCost = orderDTO.getOrderTotalCost();
//    }

    // Getters and setters
}
