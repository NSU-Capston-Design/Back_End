package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.order.OrderDTO;
import com.codingrecipe.member.dto.order.OrderItemsResDTO;
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
import com.codingrecipe.member.repository.OrderItemRepository;
import com.codingrecipe.member.repository.OrderRepository;
import com.codingrecipe.member.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
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
                throw new NotFoundProductException("상품을 찾을 수 없습니다.", orderRequestDTO.getProductId());
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

//        if (orderDTOS.size() == 1){
//            log.info("===orderDTOS.size가 1일때===");
//            OrderItem orderItem = OrderItem.createOrderItem(products.get(0), orderDTOS.get(0).getPrice(), orderDTOS.get(0).getCount());
//            orderItems.add(orderItem);
//
//            log.info("===OrderEntity 생성===");
//            OrderEntity order = OrderEntity.createOrder(memberEntity, orderItems);
//
//            log.info("===OrderEntity 저장===");
//            orderRepository.save(order);
//
//            log.info("===OrderItem에 OrderEntity set===");
//            for (OrderItem orderItem : orderItems){
//                orderItem.setOrder(order);
//            }
//
//            log.info("===orderItem 저장===");
//            orderItemRepository.saveAll(orderItems);
//        }
//        else {
            for (int i = 0; i < orderDTOS.size(); i++){
                log.info("===orderItem 생성=== i : " + i);
                OrderItem orderItem = OrderItem.createOrderItem(products.get(i), orderDTOS.get(i).getPrice(), orderDTOS.get(i).getCount());

                orderItems.add(orderItem);
            }
            log.info("===OrderEntity 생성===");
            OrderEntity order = OrderEntity.createOrder(memberEntity, orderItems);

            log.info("===OrderEntity 저장===");
            orderRepository.save(order);

            log.info("===OrderItem에 OrderEntity set===");
            for (OrderItem orderItem : orderItems){
                orderItem.setOrder(order);
                System.out.println("117line => orderItem.getCount : " + orderItem.getCount());
            }

            log.info("===orderItem 저장===");
            orderItemRepository.saveAll(orderItems);
//        }
    }

    /**
     * 주문 조회
     */
    @Transactional
    public List<OrderDTO> orders(String userId) throws NotFoundMemberException {
        log.info("===orders 호출됨=== userId : " + userId);
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(userId);
        if (byUserId.isEmpty()){
            log.info("===해당 MemberEntity 없음=== userId : " + userId);
            throw new NotFoundMemberException("사용자를 찾을 수 없습니다. userId: " + userId);
        }

        List<OrderEntity> orders = orderRepository.findByMember(byUserId.get());
        if (orders.isEmpty()){
            log.info("===멤버에 해당하는 주문이 존재하지 않음=== userId : " + userId);
            throw new NotFoundOrderException("주문이 존재하지 않습니다.");
        }

        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (OrderEntity order : orders) {
            log.info("===orderDTOList에 orderEntity 집어넣기=== ordersId : " + order.getOrderId());

            String date = localDateToDate(order.getOrderDate());// 변환한 날짜 저장

            // i번째 orderId로 orderItems 조회
            List<OrderItem> orderItems = orderItemRepository.findOrderItemByOrderId(order.getOrderId());
            List<OrderItemsResDTO> orderDTOList = new ArrayList<>();

            for (OrderItem orderItem : orderItems) {

                log.info("===orderItem Id=== : " + orderItem.getId());

                OrderItemsResDTO build = OrderItemsResDTO.builder()
                        .id(orderItem.getId())
                        .orderPrice(orderItem.getOrderPrice())
                        .count(orderItem.getCount())
                        .productName(orderItem.getProduct().getProductName())
                        .productId(orderItem.getProduct().getFileId())
                        .build();

                log.info("===productName=== : " + build.getProductName());
                orderDTOList.add(build);
            }

            OrderDTO orderDTO = OrderDTO.builder()
                    .orderId(order.getOrderId())
                    .orderDate(date)
                    .orderStatus(order.getOrderStatus())
                    .delivery(order.getDelivery())
                    .orderItems(orderDTOList)
                    .orderTotalCost(order.getTotalPrice())
                    .build();

            log.info("===orderDTOList에 넣기 성공===");
            orderDTOS.add(orderDTO);

        }

        return orderDTOS;
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


        String date = localDateToDate(orderEntity.getOrderDate()); // 날짜 포맷 변환 및 String으로 변환

        List<OrderItemsResDTO> orderItemsResDTOS = new ArrayList<>();

        log.info("===orderItems.size=== size : " + orderEntity.getOrderItems().size());
        for (int i = 0; i < orderEntity.getOrderItems().size(); i++){
            log.info("===orderItems 변환=== index : " + i);
            OrderItemsResDTO build = OrderItemsResDTO.builder()
                    .id(orderEntity.getOrderItems().get(i).getId())
                    .orderPrice(orderEntity.getOrderItems().get(i).getOrderPrice())
                    .count(orderEntity.getOrderItems().get(i).getCount())
                    .productId(orderEntity.getOrderItems().get(i).getProduct().getFileId())
                    .productName(orderEntity.getOrderItems().get(i).getProduct().getProductName())
                    .build();

            orderItemsResDTOS.add(build);
        }

        log.info("===OrderDTO 반환===");
        return   OrderDTO.builder()
                .orderId(orderEntity.getOrderId())
                .orderDate(date)
                .orderStatus(orderEntity.getOrderStatus())
                .delivery(orderEntity.getDelivery())
                .orderItems(orderItemsResDTOS)
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

    /**
     * LocalDate => Instant => Date => String
     * @return String
     */
    private String localDateToDate(LocalDateTime local){

        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd - HH:mm"); // 형식 변환

        Instant instant = local.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);

        return format.format(from);
    }
}