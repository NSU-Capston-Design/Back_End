package com.codingrecipe.member.entity;

import com.codingrecipe.member.exception.NotEnoughInvenException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@Table(name = "OrderItem")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "order_price")
    private int orderPrice;

    @Column
    private int count;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // 지연로딩 설정
    @JoinColumn(name = "file_id")
    private ProductEntity product;

    //==생성 메서드==//
    public static OrderItem createOrderItem(ProductEntity product, int orderPrice, int count) throws NotEnoughInvenException {
        log.info("주문 생성. ");
        OrderItem orderItem = new OrderItem();

        log.info("product 생성. = " +product);
        orderItem.setProduct(product);

        log.info("orderPrice 생성. = " + orderPrice);
        orderItem.setOrderPrice(orderPrice);

        log.info("count 생성. " + count);
        orderItem.setCount(count);

        product.removeInven(count);
        log.info("===orderItem 생성===");
        return orderItem;
    }

    //==비즈니스 로직==//

    /**
     * 주문취소시 재고수량 복구
     */
    public void cancel(){
        getProduct().addInven(count);
    }

    /**
     * 총 주문 금액
     */
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }

}
