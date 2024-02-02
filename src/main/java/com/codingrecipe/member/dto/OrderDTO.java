package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.OrderEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {

    private Long OrderId;                   // 주문코드 아이디
    private LocalDateTime OrderData;        // 주문일자
    private String OrderStatus;             // 주문상태
    private String OrderPaymentStatus;      // 결제상태
    private String OrderCsStatus;           // CS상태
    private String OrderProductAmount;      // 상품금액
    private String OrderShippingCost;       // 배송비
    private String OrderTotalAmount;        // 총 주문금액

    public static OrderDTO toOrderDTO(OrderEntity orderEntity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setOrderData(orderEntity.getOrderData());
        orderDTO.setOrderStatus(orderDTO.getOrderStatus());
        orderDTO.setOrderPaymentStatus(orderDTO.getOrderPaymentStatus());
        orderDTO.setOrderCsStatus(orderDTO.getOrderCsStatus());
        orderDTO.setOrderProductAmount(orderDTO.getOrderProductAmount());
        orderDTO.setOrderShippingCost(orderDTO.getOrderShippingCost());
        orderDTO.setOrderTotalAmount(orderDTO.getOrderTotalAmount());
        return orderDTO;
    }

}
