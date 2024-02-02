package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.OrderDTO;
import com.codingrecipe.member.dto.ProductDTO;
import com.codingrecipe.member.dto.ProductDetail;
import com.codingrecipe.member.repository.OrderRepository;
import com.codingrecipe.member.repository.MemberRepository;
import com.codingrecipe.member.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository ProductRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDTO orderDto, String email) {
        ProductDTO product = ProductRepository.findById(orderDto.getproductId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}

