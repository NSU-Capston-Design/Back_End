package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@Table(name = "uploaded_file")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String productName;

    @Column
    private long fileSize;

    @Column
    private String fileType;

    @Column
    private LocalDateTime uploadTime;

    @Column
    private int productPrice;

    @Column(name = "product_url")
    private String productURL;

    @Column(name = "product_inven")
    private int productInven;

    public ProductEntity() {
    }

    @Transactional
    public void createProduct(ProductDTO productDTO) {

        this.productName = productDTO.getProductName();
        this.fileSize = productDTO.getFileSize();
        this.fileType = productDTO.getFileType();
        this.uploadTime = productDTO.getUploadTime();
        this.productPrice = productDTO.getProductPrice();
        this.productURL = productDTO.getProductURL();

    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private MemberEntity memberEntity;

}
