package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.dto.order.OrderItmesDTO;
import com.codingrecipe.member.dto.order.OrderRequestDTO;
import com.codingrecipe.member.entity.OrderEntity;
import com.codingrecipe.member.exception.NotEnoughInvenException;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.exception.NotFoundOrderException;
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
    public ResponseEntity<String> createOrder(@RequestBody OrderItmesDTO orderItmesDTO){
        try {
            orderService.createOrder(orderItmesDTO);
        } catch (NotEnoughInvenException | NotFoundProductException | NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("ok");
    }

    /**
     * 주문 내역 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(@RequestParam("userId") String userId){
        try {
            List<OrderDTO> orders = orderService.orders(userId);
            return ResponseEntity.ok(orders);
        } catch (NotFoundMemberException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") long orderId) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(order);
        } catch (NotFoundOrderException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 주문 취소
     */
    @DeleteMapping("/order/delete/{orderId}")
    public void deleteOrder(@PathVariable("orderId") long orderId) {
        orderService.deleteOrder(orderId);
    }
}