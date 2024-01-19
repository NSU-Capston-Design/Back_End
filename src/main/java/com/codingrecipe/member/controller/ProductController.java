package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.ProductDTO;
import com.codingrecipe.member.dto.ProductData;
import com.codingrecipe.member.dto.ProductDetail;
import com.codingrecipe.member.dto.ProductRequest;
import com.codingrecipe.member.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping(value = "/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart(value = "file") MultipartFile file,
                                                   @RequestPart(value = "data") ProductData data) {
    // multipart/form-data 객체와, application/json 객체 두 가지를 받음
    // data: { 상품 이름, 상품 가격, 유저이름 }이 담겨 있음. 이걸 따로 Dto에 저장해서 엔티티로 변환.

        try {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setFile(file);
            productRequest.setData(data);
            productService.uploadFile(productRequest);

            return ResponseEntity.ok("등록 성공!");
        } catch (Exception e){

            return ResponseEntity.status(500).body("상품 등록 실패...");
        }

    }

    @GetMapping("/product/list")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> fileDTOList = productService.productList();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용

        return ResponseEntity.ok(fileDTOList);
    } // 관리자모드 회원목록

    @GetMapping("/product/detail")
    public ResponseEntity<ProductDetail> productDetail(@RequestParam(name = "productId") String productId){    //
        try {

            long id = Long.parseLong(productId);    //useParams인한 String을 Long으로 변환
            ProductDetail productDetail = productService.productDetail(id);
            return ResponseEntity.ok(productDetail);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
