package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem o where o.order.orderId = :orderId")
    List<OrderItem> findOrderItemByOrderId(long orderId);

}
