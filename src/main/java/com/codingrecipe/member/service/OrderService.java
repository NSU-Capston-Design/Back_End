package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.OrderEntity;
import com.codingrecipe.member.entity.OrderItem;
import com.codingrecipe.member.entity.ProductEntity;
import com.codingrecipe.member.exception.NotEnoughInvenException;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.exception.NotFoundProductException;
import com.codingrecipe.member.repository.MemberRepository;
import com.codingrecipe.member.repository.OrderRepository;
import com.codingrecipe.member.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    /**
     * 주문 생성
     */
    public void createOrder(int[] orderPrices, int[] counts, Long memberId, long[] productId) throws NotFoundProductException, NotEnoughInvenException, NotFoundMemberException {

        log.info("===주문 생성===");
        List<ProductEntity> products = new ArrayList<>();       // 받은 상품들을 넣을 가변 배열 List 생성

        log.info("===productId로 상품조회===");
        for (Long product : productId){                         // for each문을 통하여 순회
            ProductEntity byFileId = productRepository.findByFileId(product);           // 각 상품 조회
            if (byFileId == null){                              // null이라면, 예외처리
                log.info("===상품 조회 실패===");
                throw new NotFoundProductException("상품을 찾을 수 없습니다.", product);
            }

            products.add(byFileId);                             // 리스트에 담기
        }

        log.info("===유저 조회===");
        Optional<MemberEntity> byMemberId = memberRepository.findById(memberId);    // memberId를 통해 유저 찾기
        if (byMemberId.isEmpty()){                                                  // 만약 없는 id라면 예외처리
            log.info("===유저 조회 실패=== memberId :" + memberId);
            throw new NotFoundMemberException("사용자를 찾을 수 없습니다.");
        }

        List<OrderItem> orderItems = new ArrayList<>();                             // orderItem을 생성 후 담을 List 생성
        log.info("===orderItems 생성===");
        for (int i = 0; i <products.size(); i++){                                   // 순회하면서 담기
            ProductEntity product = products.get(i);
            int orderPrice = orderPrices[i];
            int count = counts[i];
            orderItems.add(OrderItem.createOrderItem(product, orderPrice, count));
        }

        log.info("===List인 orderItems 배열[]로 변환===");
        OrderItem[] ArrayOrderItems = orderItems.toArray(new OrderItem[0]);

        log.info("===order생성===");
        OrderEntity order = OrderEntity.createOrder(byMemberId.get(), ArrayOrderItems);

        orderRepository.save(order);
    }

    /**
     * 주문 조회
     */
    public List<OrderDTO> orders(String userId) throws NotFoundMemberException {
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()){
            throw new NotFoundMemberException("사용자를 찾을 수 없습니다. userId: " + userId);
        }
        List<OrderEntity> orders = orderRepository.findByMember(byUserId.get());

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++){
            OrderEntity orderEntity = orders.get(i);
            OrderDTO build = new OrderDTO.Builder()
                    .orderId(orderEntity.getOrderId())
                    .orderDate(orderEntity.getOrderDate())
                    .orderStatus(orderEntity.getOrderStatus())
                    .delivery(orderEntity.getDelivery())
                    .orderItems(orderEntity.getOrderItems())
                    .orderTotalCost(orderEntity.getTotalPrice())
                    .build();

            orderDTOList.add(build);
        }

        return orderDTOList;

    }
}