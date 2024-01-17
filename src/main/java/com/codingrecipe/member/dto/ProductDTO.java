package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.ProductEntity;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    private Long productId;
    private String productName; // 상품이름
    private long fileSize;  // 파일 사이즈
    private String fileType;    // 파일타입
    private LocalDateTime uploadTime;   // 업로드 시간
    private int productPrice;   // 상품 가격
    private String productURL;  // 상품 이미지 저장 위치
    private int productInven;   // 재고수량
    private int productView;    // 조회수

    private String userId;
    // 생성자, 게터 및 세터는 필요에 따라 추가할 수 있습니다.

    // ... Getter, Setter


    public ProductDTO(String productName, long fileSize,
                      String fileType, LocalDateTime uploadTime,
                      int productPrice, String productURL, int productInven) {

        this.productName = productName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.uploadTime = uploadTime;
        this.productPrice = productPrice;
        this.productURL = productURL;
        this.productInven = productInven;
    }

    public static ProductDTO toFileDTO(ProductEntity productEntity){    // DTO 엔티티내용으로 업데이트
        ProductDTO fileUploadDTO = new ProductDTO();
        fileUploadDTO.setProductId(productEntity.getId());
        fileUploadDTO.setProductName(productEntity.getProductName());
        fileUploadDTO.setFileSize(productEntity.getFileSize());
        fileUploadDTO.setFileType(productEntity.getFileType());
        fileUploadDTO.setUploadTime(productEntity.getUploadTime());
        fileUploadDTO.setProductPrice(productEntity.getProductPrice());
        fileUploadDTO.setProductURL(productEntity.getProductURL());
        fileUploadDTO.setProductInven(productEntity.getProductInven());
        fileUploadDTO.setProductView(productEntity.getProductView());
        return fileUploadDTO;
    }
}
