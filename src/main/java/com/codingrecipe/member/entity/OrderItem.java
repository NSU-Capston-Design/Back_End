package com.codingrecipe.member.entity;

import com.codingrecipe.member.exception.NotEnoughInvenException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
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
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        product.removeInven(count);
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
