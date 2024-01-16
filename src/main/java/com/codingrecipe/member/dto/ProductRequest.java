package com.codingrecipe.member.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter @Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private MultipartFile file;
    private ProductData data;

}

