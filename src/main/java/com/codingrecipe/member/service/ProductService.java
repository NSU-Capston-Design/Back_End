package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.product.*;
import com.codingrecipe.member.entity.enums.Category;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.ProductEntity;
import com.codingrecipe.member.exception.NotFoundFileException;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.exception.NotFoundProductException;
import com.codingrecipe.member.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberService memberService;

    @Value("${upload.dir}" + "/postPhoto")
    private String uploadDir;

    /**
     * 파일 업로드
     */
    public void uploadFile(ProductRequest productRequest) {          // 파일을 매개변수로 받음
        try {
            System.out.println("service계층 Inven : " + productRequest.getData().getProductInven());
            MultipartFile file = productRequest.getFile();
            log.info("===MultipartFile file에 담기 성공===");

            String produtName = productRequest.getData().getProductName();
            int price = productRequest.getData().getProductPrice();
            int productInven = productRequest.getData().getProductInven();

            MemberEntity byMemberId = memberService.findByMemberId(productRequest.getData().getMemberId()).get();
            log.info("===member 찾기 성공=== memberId : " + byMemberId.getUserId());

            Category category = Category.TOP;    // 카테고리 뽑기
            log.info("===Category 주입 성공=== : " + category);

            Date currentDate = new Date(); // 현재 시간
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd - HH:mm"); // 형식 변환
            String date = format.format(currentDate);   // 변환한 날짜 저장

            log.info("===날짜의 형식 변환 및 저장 성공=== : " + date);

            String originProductName = produtName;

            UUID uuid = UUID.randomUUID();
            log.info("===uuid 생성 성공=== : " + uuid);

            String extension = file.getContentType();
            log.info("===extention(확장자) 저장 성공=== : " + extension);

            String saveFileName = uuid + "." + extension.substring(extension.length() - 3);;
            log.info("===saveFileName 생성 성공=== : " + saveFileName);

            String fileUpload = uploadDir + "/" + saveFileName;  // upload 절대 주소
            log.info("===fileUpload 생성 성공=== : " + fileUpload);
//            String replaceProductName = originProductName.replaceAll("\\s", "_");   // 공백을 언더바로 치환
//            String filePath = uploadDir + File.separator + replaceProductName;   // 파일 경로 설정 (uploadDir에는 static 포토주소 있음)

            File dest = new File(fileUpload);         // 파일 객체 dest를 생성 하고, 파일 경로를 넣음
            file.transferTo(dest);    // transferTo 메서드는 MultipartFile에서 실제 파일로의 이동 또는 복사를 수행하는 메서드. (절대경로에 저장)

            String filePath = "/photos/postPhoto/" + saveFileName;   // 상대경로로 변경


            System.out.println("productInven = " + productInven);
            ProductDTO fileUploadDTO = new ProductDTO(produtName, file.getSize(),
                    file.getContentType(), date, price, filePath, productInven, byMemberId, category);

            System.out.println("fileUploadDTO.getProductInven() = " + fileUploadDTO.getProductInven());
            // DTO에서 엔터티로 변환
            ProductEntity uploadedFile = new ProductEntity();
            uploadedFile.createProduct(fileUploadDTO);


            // 엔터티 저장
            productRepository.save(uploadedFile);

            System.out.println("uploadedFile = " + uploadedFile);
        } catch (IOException e) {
            System.out.println("e = " + e.getMessage());
        } catch (NotFoundMemberException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 카테고리 메서드
     */
//    private static Category getCategory(ProductRequest productRequest) {
//        String categories = productRequest.getData().getCategory();
//        Category category = Category.DEFAULT;
//        if (categories.equals("TOP")){
//            category = Category.TOP;
//        } else if (categories.equals("PANTS")) {
//            category = Category.PANTS;
//        } else if(categories.equals("SHOES")){
//            category = Category.SHOES;
//        } else if(categories.equals("BAG")){
//            category = Category.BAG;
//        } else if(categories.equals("ELECTRONICS")){
//            category = Category.ELECTRONICS;
//        } else {
//            if (categories.isEmpty()){
//                throw new NullPointerException("입력된 값이 없습니다.");
//            }
//        }
//        return category;
//    }

    /**
     * @return 상품 리스트
     */
    public List<ProductListDTO> productList(){      // 상품 리스트 반환하기
        List<ProductEntity> fileEntityList = productRepository.findAll();
        List<ProductListDTO> fileDTOList = new ArrayList<>();

        for (ProductEntity productEntity : fileEntityList){
            ProductListDTO fileUploadDTO = ProductListDTO.toListDTO(productEntity);

            fileDTOList.add(fileUploadDTO);
        }

        return fileDTOList;
    }

    /**
     * 상품 디테일
     * 동작할 때, 조회수가 하나 올라감
     */
    public ProductDetail productDetail(Long id) throws EntityNotFoundException { // 조회수 업데이트 및 상품 디테일
        Optional<ProductEntity> findId = productRepository.findById(id);
        if(findId.isPresent()) {
            ProductEntity productEntity = findId.get();
            int findProductView = productEntity.getProductView();
            productEntity.setProductView(findProductView + 1);

            return new ProductDetail(productEntity);
        } else {
            throw new EntityNotFoundException("해당 상품을 찾을 수 없습니다.");
        }
    }

    /**
     * 조회수 순으로 상품 3개를 가져옴(메인화면에 쓰이는 용도)
     */
    public List<ProductDTO> findTopView() throws NotFoundProductException {
        Optional<List<ProductEntity>> top3ProductOptional = productRepository.findTop3ByProductView();
        List<ProductDTO> top3ProductDto = new ArrayList<>();

        if (top3ProductOptional.isEmpty()){
            throw new NotFoundProductException("상품이 없습니다.");
        }
        List<ProductEntity> top3Product = top3ProductOptional.get();

        if (top3Product.size() < 3){
            for (int i = 0; i < top3Product.size(); i++){
                ProductEntity productEntity = top3Product.get(i);
                if(productEntity == null){
                    throw new NotFoundProductException("상품이 없습니다.");
                }
                top3ProductDto.add(new ProductDTO(productEntity));
            }
        }
        else {
            for (int i = 0; i < 3; i++) {   // 스프링 데이터 JPA가 작동 안해서, 수동으로 넣어둠.
                ProductEntity productEntity = top3Product.get(i);
                if (productEntity == null) {
                    throw new NotFoundProductException("상품이 없습니다.");
                }
                top3ProductDto.add(new ProductDTO(productEntity));
            }
        }

        System.out.println("top3Product = " + top3Product);

        System.out.println("top3ProductDto = " + top3ProductDto);

        return top3ProductDto;
    }

    /**
     * 상품 id들을 받아서 가져옴(장바구니)
     */
    public List<ProductBuyListDTO> findAllByProductId(long[] productId) throws NotFoundProductException {
        List<ProductEntity> allByProductId = productRepository.findAllByProductId(productId);
        if (allByProductId.isEmpty()){
            throw new NotFoundProductException("없는 상품 ID입니다.");
        }
        List<ProductBuyListDTO> allByProductIdDto = new ArrayList<>();

        for (ProductEntity productEntity : allByProductId){
            ProductBuyListDTO productDTO = new ProductBuyListDTO(productEntity);

            allByProductIdDto.add(productDTO);
        }
        return allByProductIdDto;
    }

    /**
     * 상품 삭제
     */
    public String deleteProduct(Long id) throws NotFoundProductException, NotFoundFileException {
        Optional<ProductEntity> byId = productRepository.findById(id);
        if (byId.isEmpty()){
            throw new NotFoundProductException("상품을 찾을 수 없습니다.");
        } else {
            String productURL = byId.get().getProductURL();
            String filePath = uploadDir + productURL;
            File deleteFile = new File(filePath);
            if (deleteFile.exists()){
                deleteFile.delete();
                productRepository.deleteById(id);
                return "ok";
            } else {
                productRepository.deleteById(id);
                throw new NotFoundFileException("파일을 찾을 수 없습니다.");
            }
        }
    }

}
