package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.ProductDTO;
import com.codingrecipe.member.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")    //조회수 상위 3개의 상품 가져옴.
    public ResponseEntity<List<ProductDTO>> home(){
        System.out.println(" 호출됨 ");
        List<ProductDTO> topView = productService.findTopView();
        return ResponseEntity.ok(topView);
    }


}
