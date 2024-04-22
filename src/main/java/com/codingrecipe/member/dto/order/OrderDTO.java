package com.codingrecipe.member.dto.order;

import com.codingrecipe.member.entity.OrderItem;
import com.codingrecipe.member.entity.enums.DeliveryStatus;
import com.codingrecipe.member.entity.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;            // 주문상태
    private DeliveryStatus delivery;      // 배송 상태 (BEFO, SHIP, COMP)
    private List<OrderItem> orderItems = new ArrayList<>();
    private int orderTotalCost;

    // private로 생성자로 생성하는걸 막음
    private OrderDTO(){}

    /**
     * 빌더
     */
    public static class Builder{
        private Long orderId;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;            // 주문상태
        private DeliveryStatus delivery;      // 배송 상태 (BEFO, SHIP, COMP)
        private List<OrderItem> orderItems = new ArrayList<>();
        private int orderTotalCost;


        public Builder orderId(Long orderId){
            this.orderId = orderId;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate){
            this.orderDate = orderDate;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus){
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder delivery(DeliveryStatus delivery){
            this.delivery = delivery;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems){
            this.orderItems = orderItems;
            return this;
        }

        public Builder orderTotalCost(int orderTotalCost){
            this.orderTotalCost = orderTotalCost;
            return this;
        }

        public OrderDTO build() {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(this.orderId);
            orderDTO.setOrderDate(this.orderDate);
            orderDTO.setOrderStatus(this.orderStatus);
            orderDTO.setDelivery(this.delivery);
            orderDTO.setOrderItems(this.orderItems);
            orderDTO.setOrderTotalCost(this.orderTotalCost);
            return orderDTO;
        }

    }
}
