package com.codingrecipe.member.dto.product;

import com.codingrecipe.member.dto.member.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.ProductEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    private Long fileId;
    private String productName; // 상품이름
    private long fileSize;  // 파일 사이즈
    private String fileType;    // 파일타입
    private String uploadTime;   // 업로드 시간
    private int productPrice;   // 상품 가격
    private String productURL;  // 상품 이미지 저장 위치
    private int productInven;   // 재고수량
    private int productView;    // 조회수

    private MemberEntity memberEntity;
    // 생성자, 게터 및 세터는 필요에 따라 추가할 수 있습니다.

    // ... Getter, Setter


    public ProductDTO(String productName, long fileSize,
                      String fileType, String uploadTime,
                      int productPrice, String productURL, int productInven, MemberEntity memberEntity) {

        this.productName = productName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.uploadTime = uploadTime;
        this.productPrice = productPrice;
        this.productURL = productURL;
        this.productInven = productInven;
        this.memberEntity = memberEntity;
    }

    public ProductDTO(ProductEntity productEntity) {    // 엔티티를 DTO로 변환
        this.fileId = productEntity.getFileId();
        this.productName = productEntity.getProductName();
        this.fileSize = productEntity.getFileSize();
        this.fileType = productEntity.getFileType();
        this.uploadTime = productEntity.getUploadTime();
        this.productPrice = productEntity.getProductPrice();
        this.productURL = productEntity.getProductURL();
        this.productInven = productEntity.getProductInven();
        this.productView = productEntity.getProductView();
    }

    public static ProductDTO toFileDTO(ProductEntity productEntity){    // DTO 엔티티내용으로 업데이트
        ProductDTO fileUploadDTO = new ProductDTO();
        fileUploadDTO.setFileId(productEntity.getFileId());
        fileUploadDTO.setProductName(productEntity.getProductName());
        fileUploadDTO.setFileSize(productEntity.getFileSize());
        fileUploadDTO.setFileType(productEntity.getFileType());
        fileUploadDTO.setUploadTime(productEntity.getUploadTime());
        fileUploadDTO.setProductPrice(productEntity.getProductPrice());
        fileUploadDTO.setProductURL(productEntity.getProductURL());
        fileUploadDTO.setProductInven(productEntity.getProductInven());
        fileUploadDTO.setProductView(productEntity.getProductView());
        fileUploadDTO.setMemberEntity(productEntity.getMemberEntity());
        return fileUploadDTO;
    }
}
