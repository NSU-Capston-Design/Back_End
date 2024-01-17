package com.codingrecipe.member.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {   // 사진 및 데이터를 받기 위한 DTO

    private MultipartFile file;
    private ProductData data;

}

