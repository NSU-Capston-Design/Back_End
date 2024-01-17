package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.ProductEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ProductDetail {    // 상품 상세페이지를 위한 DTO

    private String productName;
    private LocalDateTime uploadTime;
    private int productPrice;
    private String productURL;
    private int productInven;
    private int productView;

    public ProductDetail(ProductEntity productEntity) {
        this.productName = productEntity.getProductName();
        this.uploadTime = productEntity.getUploadTime();
        this.productPrice = productEntity.getProductPrice();
        this.productURL = productEntity.getProductURL();
        this.productInven = productEntity.getProductInven();
        this.productView = productEntity.getProductView();
    }

}
