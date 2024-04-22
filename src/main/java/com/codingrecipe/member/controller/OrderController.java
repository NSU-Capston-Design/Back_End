package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.dto.order.OrderRequestDTO;
import com.codingrecipe.member.entity.OrderEntity;
import com.codingrecipe.member.exception.NotEnoughInvenException;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.exception.NotFoundProductException;
import com.codingrecipe.member.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 주문하기
     */
    @PostMapping(value = "/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDTO orderDTO){
        try {
            orderService.createOrder(orderDTO.getPrice(), orderDTO.getCount(), orderDTO.getMemberId(), orderDTO.getProductId());
        } catch (NotEnoughInvenException | NotFoundProductException | NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("ok");
    }

    /**
     * 주문 내역 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getOrderById(@RequestParam("userId") String userId){
        try {
            List<OrderDTO> orders = orderService.orders(userId);
            return ResponseEntity.ok(orders);
        } catch (NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public List<OrderRequestDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/update/{orderId}")
    public OrderEntity updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDTO orderDTO) {
        return orderService.updateOrder(orderId, orderDTO);
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}