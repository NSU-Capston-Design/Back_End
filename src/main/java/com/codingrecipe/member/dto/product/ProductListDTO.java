package com.codingrecipe.member.dto.product;

import com.codingrecipe.member.entity.ProductEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ProductListDTO {

    private Long fileId;
    private String productName;
    private String uploadTime;
    private int productPrice;
    private String productURL;
    private int productInven;
    private int productView;
    private String userName;

    public static ProductListDTO toListDTO(ProductEntity productEntity){    // DTO 엔티티내용으로 업데이트
        ProductListDTO fileListDTO = new ProductListDTO();
        fileListDTO.setFileId(productEntity.getFileId());
        fileListDTO.setProductName(productEntity.getProductName());
        fileListDTO.setUploadTime(productEntity.getUploadTime());
        fileListDTO.setProductPrice(productEntity.getProductPrice());
        fileListDTO.setProductURL(productEntity.getProductURL());
        fileListDTO.setProductInven(productEntity.getProductInven());
        fileListDTO.setProductView(productEntity.getProductView());
        fileListDTO.setUserName(productEntity.getMemberEntity().getUserName());
        return fileListDTO;
    }
}
