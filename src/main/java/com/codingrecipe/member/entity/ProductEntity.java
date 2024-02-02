package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.product.ProductDTO;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_table")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;        // 상품 아이디

    @Column
    private String productName;     // 상품 이름

    @Column
    private long fileSize;          // 사진 크기

    @Column
    private String fileType;        // 파일 타입 (jpg, png 같은)

    @Column
    private String uploadTime;   // 업로드 시간

    @Column
    private int productPrice;       // 상품 가격

    @Column(name = "product_url")
    private String productURL;      // 상품 사진 주소

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int productInven;   // 상풍 재고수량

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int productView;   // 상품 조회수


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Transactional
    public void createProduct(ProductDTO productDTO) {  //DTO를 받아 Entity에 저장하는 메서드

        this.productName = productDTO.getProductName();
        this.fileSize = productDTO.getFileSize();
        this.fileType = productDTO.getFileType();
        this.uploadTime = productDTO.getUploadTime();
        this.productPrice = productDTO.getProductPrice();
        this.productURL = productDTO.getProductURL();
        this.memberEntity = productDTO.getMemberEntity();

    }

}
