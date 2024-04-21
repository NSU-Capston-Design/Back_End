package com.codingrecipe.member.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
public class ProductData {          // Json데이터를 정의하기 위한 DTO
    private String productName;         // 상품명
    private int productPrice;           // 상품가격
    private int productInven;           // 재고수량
    private Long memberId;          // 작성자 id
    private String category;      // 카테고리
    public ProductData(String productName, int productPrice, int productInven, int memberId, String category) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInven = productInven;
        this.memberId = (long)memberId;
        this.category = category;
    }
}
