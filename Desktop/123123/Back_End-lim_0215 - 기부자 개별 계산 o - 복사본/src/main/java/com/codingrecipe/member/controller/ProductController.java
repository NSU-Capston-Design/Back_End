package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.product.*;
import com.codingrecipe.member.exception.NotFoundProductException;
import com.codingrecipe.member.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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

            return ResponseEntity.ok("등록 성공! \n" + data.getMemberId());
        } catch (Exception e){

            return ResponseEntity.status(500).body("상품 등록 실패...");
        }

    }

    /**
     * 상품 전체리스트
     */
    @GetMapping("/product/list")
    public ResponseEntity<List<ProductListDTO>> findAll() {
        List<ProductListDTO> fileDTOList = productService.productList();

        return ResponseEntity.ok(fileDTOList);
    }

    // 관리자모드 회원목록 제작필요

    /**
     * 상품 상세리스트
     */
    @GetMapping("/product/detail")
    public ResponseEntity<ProductDetail> productDetail(@RequestParam(name = "fileId") String fileId){
        try {

            long id = Long.parseLong(fileId);    //useParams인한 String을 Long으로 변환
            ProductDetail productDetail = productService.productDetail(id);
            return ResponseEntity.ok(productDetail);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 상품 장바구니 리스트
     */
    @GetMapping("/product/buyList")
    public ResponseEntity<List<ProductBuyListDTO>> findBuyList(@RequestParam(name = "fileIds") String ...fileId){
        try {
            long[] id = new long[fileId.length];
            for (int i = 0; i < fileId.length; i++){
                id[i] = Long.parseLong(fileId[i]);    //useParams인한 String을 Long으로 변환
            }
            List<ProductBuyListDTO> allByProductId = productService.findAllByProductId(id);

            return ResponseEntity.ok(allByProductId);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (NotFoundProductException e) {
            throw new RuntimeException(e);
        }
    }

}
