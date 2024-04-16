package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.entity.OrderEntity;
import com.codingrecipe.member.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity createOrder(OrderDTO orderDTO) { // 주문생성
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(orderDTO.getOrderDate());
        orderEntity.setOrderStatus(orderDTO.getOrderStatus());
        orderEntity.setOrderPaymentStatus(orderDTO.getOrderPaymentStatus());
        orderEntity.setProductPrice(orderDTO.getProductPrice());
        orderEntity.setProductName(orderDTO.getProductName());
        orderEntity.setOrderShippingCost(orderDTO.getOrderShippingCost());
        // 주문 총 비용 = 상품 가격 + 주문 배송 비용
        int orderTotalCost = orderDTO.getProductPrice() + orderDTO.getOrderShippingCost();
        orderEntity.setOrderTotalCost(orderTotalCost);
        // 다른 속성 설정이 필요한 경우 여기에 추가
        return orderRepository.save(orderEntity);
    }

    public OrderDTO getOrderById(Long orderId) {

        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return new OrderDTO(orderEntity);
    }

    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        return orderEntities.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    public OrderEntity updateOrder(Long orderId, OrderDTO orderDTO) {
        // 주문 엔티티를 주문 ID로 조회합니다.
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 ID " + orderId + "에 해당하는 주문을 찾을 수 없습니다."));

        // 주문 엔티티의 속성을 주문 DTO를 사용하여 업데이트합니다.
        // 여기서는 주문 DTO에 선택된 상품 및 가격 정보가 포함되어 있다고 가정합니다.

        // 주문 상태 및 결제 상태를 업데이트합니다.
        orderEntity.setOrderStatus(orderDTO.getOrderStatus());
        orderEntity.setOrderPaymentStatus(orderDTO.getOrderPaymentStatus());

        // 상품 가격을 업데이트합니다.
        orderEntity.setProductPrice(orderDTO.getProductPrice());

        // 상품 이름을 업데이트합니다.
        orderEntity.setProductName(orderDTO.getProductName());

        // 주문 배송 비용을 업데이트합니다.
        orderEntity.setOrderShippingCost(orderDTO.getOrderShippingCost());

        // 주문 총 비용을 업데이트합니다.
        orderEntity.setOrderTotalCost(orderDTO.getOrderTotalCost());

//        // 수량을 업데이트합니다.
//        orderEntity.setQuantity(orderDTO.getQuantity());

        // 업데이트된 주문 엔티티를 저장합니다.
        return orderRepository.save(orderEntity);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}