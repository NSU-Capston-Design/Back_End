package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.dto.order.OrderItmesDTO;
import com.codingrecipe.member.dto.order.OrderRequestDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.OrderEntity;
import com.codingrecipe.member.entity.OrderItem;
import com.codingrecipe.member.entity.ProductEntity;
import com.codingrecipe.member.exception.NotEnoughInvenException;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.exception.NotFoundOrderException;
import com.codingrecipe.member.exception.NotFoundProductException;
import com.codingrecipe.member.repository.MemberRepository;
import com.codingrecipe.member.repository.OrderRepository;
import com.codingrecipe.member.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    @Transactional
    public void createOrder(OrderItmesDTO orderItmesDTO) throws NotFoundProductException, NotEnoughInvenException, NotFoundMemberException {

        log.info("===주문 생성===");

        for (int i = 0; i < orderItmesDTO.getOrderRequestDTOS().size(); i++){
            System.out.println("count = " + orderItmesDTO.getOrderRequestDTOS().get(i).getCount());
        }
        List<ProductEntity> products = new ArrayList<>();       // 받은 상품들을 넣을 가변 배열 List 생성

        String userId = orderItmesDTO.getUserId();              // 주문한 유저정보 가져오기
        List<OrderRequestDTO> orderDTOS = orderItmesDTO.getOrderRequestDTOS(); // 주문받은 리스트 빼서 저장

        for (int i = 0; i < orderDTOS.size(); i++){                 // for문으로 주문받은 products들 조회
            OrderRequestDTO orderRequestDTO = orderDTOS.get(i);
            log.info("===productId로 상품조회=== fileId : " + orderRequestDTO.getProductId());
            Optional<ProductEntity> byFileId = productRepository.findByFileId(orderRequestDTO.getProductId());
            if (byFileId.isEmpty()){        // byfileId가 존재 안할시에 예외처리
                log.info("===상품이 존재하지 않음=== fileId : " + orderRequestDTO.getProductId());
                throw new NotFoundProductException("상품을 찾을 수 없습니다.", byFileId.get().getFileId());
            }
            products.add(byFileId.get());           // List product에 추가
        }

        log.info("===유저 조회===");
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userId);// userId를 통해 유저 찾기
        if (byUserId.isEmpty()){                                                  // 만약 없는 id라면 예외처리
            log.info("===유저 조회 실패=== userId :" + byUserId.get());
            throw new NotFoundMemberException("사용자를 찾을 수 없습니다.");
        }
        MemberEntity memberEntity = byUserId.get();

        List<OrderItem> orderItems = new ArrayList<>(orderDTOS.size());                             // orderItem을 생성 후 담을 List 생성
        log.info("===List<orderItems>에 orderItem 생성 후 넣기===");

        if (orderDTOS.size() == 1){
            log.info("===orderDTOS.size가 1일때===");
            OrderItem orderItem = OrderItem.createOrderItem(products.get(0), orderDTOS.get(0).getPrice(), orderDTOS.get(0).getCount());
            orderItems.add(orderItem);

            log.info("===OrderEntity 생성===");
            OrderEntity order = OrderEntity.createOrder(memberEntity, orderItems);

            log.info("===OrderEntity 저장===");
            orderRepository.save(order);
        }
        else {
            for (int i = 0; i < orderDTOS.size(); i++){
                log.info("===orderItem 생성=== i : " + i);
                OrderItem orderItem = OrderItem.createOrderItem(products.get(i), orderDTOS.get(i).getPrice(), orderDTOS.get(i).getCount());

                orderItems.add(orderItem);
            }
            log.info("===OrderEntity 생성===");
            OrderEntity order = OrderEntity.createOrder(memberEntity, orderItems);

            log.info("===OrderEntity 저장===");
            orderRepository.save(order);
        }
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

    /**
     * 주문 상세 조회
     */
    public OrderDTO getOrder(long orderId){
        log.info("===주문 상세 조회=== orderId : " + orderId);
        Optional<OrderEntity> byId = orderRepository.findById(orderId);
        if (byId.isEmpty()){
            log.info("===주문 조회 실패=== orderId : " + orderId);
            throw new NotFoundOrderException("주문 정보를 조회할 수 없습니다. orderId : " + orderId);
        }
        OrderEntity orderEntity = byId.get();
        log.info("===[Builder]OrderEntity를 OrderDTO로 변환===");

        return  new OrderDTO.Builder()
                .orderId(orderEntity.getOrderId())
                .orderDate(orderEntity.getOrderDate())
                .orderStatus(orderEntity.getOrderStatus())
                .delivery(orderEntity.getDelivery())
                .orderItems(orderEntity.getOrderItems())
                .orderTotalCost(orderEntity.getTotalPrice())
                .build();
    }

    /**
     * 주문 취소
     */
    public void deleteOrder(long orderId){
        try {
            log.info("===주문 취소=== orderId : " + orderId);
            Optional<OrderEntity> byId = orderRepository.findById(orderId);
            if (byId.isEmpty()){
                log.info("===주문 조회 실패=== orderId : " + orderId);
                throw new NotFoundOrderException("주문 정보를 조회할 수 없습니다. orderId : " + orderId);
            }
            log.info("===주문 취소 상품재고 복구===");
            byId.get().cancel();
            
            log.info("===주문 삭제===");
            orderRepository.deleteById(orderId);
            log.info("===주문 삭제 성공===");
        } catch (NotFoundOrderException | IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 주문 수정
     */
    public void updateOrder(long id, int count){
        Optional<OrderEntity> byId = orderRepository.findById(id);
        if (byId.isEmpty()){
            throw new NotFoundOrderException("주문이 존재하지 않습니다.");
        }
        for (int i = 0; i < byId.get().getOrderItems().size(); i++){
            byId.get().getOrderItems().get(i).setCount(count);
        }

    }
}