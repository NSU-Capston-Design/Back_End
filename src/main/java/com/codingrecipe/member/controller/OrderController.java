package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.entity.OrderEntity;
import com.codingrecipe.member.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/orders")
    public OrderEntity createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping("/get/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/getAll")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/update/{orderId}")
    public OrderEntity updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(orderId, orderDTO);
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}