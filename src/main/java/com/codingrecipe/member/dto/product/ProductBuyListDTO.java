package com.codingrecipe.member.dto.product;

import com.codingrecipe.member.entity.Category;
import com.codingrecipe.member.entity.ProductEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductBuyListDTO {
    private String productName;
    private int productPrice;
    private String productURL;      // 상품 사진 주소
    private int productInven;   // 상풍 재고수량
    private Category category;
    private String userName;

    public ProductBuyListDTO(ProductEntity productEntity) {
        this.productName = productEntity.getProductName();
        this.productPrice = productEntity.getProductPrice();
        this.productURL = productEntity.getProductURL();
        this.productInven = productEntity.getProductInven();
        this.category = productEntity.getCategory();
        this.userName = productEntity.getMemberEntity().getUserName();
    }
}


