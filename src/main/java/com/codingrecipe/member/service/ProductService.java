package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.ProductDTO;
import com.codingrecipe.member.dto.ProductData;
import com.codingrecipe.member.dto.ProductDetail;
import com.codingrecipe.member.dto.ProductRequest;
import com.codingrecipe.member.entity.ProductEntity;
import com.codingrecipe.member.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

//    @Autowired
//    private MemberRepository memberRepository;


    @Value("${upload.dir}")
    private String uploadDir;



    public String uploadFile(ProductRequest productRequest) {          // 파일을 매개변수로 받음
        try {
            MultipartFile file = productRequest.getFile();
            String produtName = productRequest.getData().getProductName();
            int price = productRequest.getData().getProductPrice();
            String userName = productRequest.getData().getUserName();
            int productInven = productRequest.getData().getProductInven();

            String originProductName = produtName;
            String replaceProductName = originProductName.replaceAll("\\s", "_");   // 공백을 언더바로 치환

            String filePath = uploadDir + File.separator + replaceProductName;   // 파일 경로 설정 (uploadDir에는 static 포토주소 있음)
            File dest = new File(filePath);         // 파일 객체 dest를 생성 하고, 파일 경로를 넣음
            file.transferTo(dest);    // transferTo 메서드는 MultipartFile에서 실제 파일로의 이동 또는 복사를 수행하는 메서드. (절대경로에 저장)

            filePath = "/photos/postPhoto/" + replaceProductName;   // 상대경로로 변경



            ProductDTO fileUploadDTO = new ProductDTO(produtName, file.getSize(),
                    file.getContentType(), LocalDateTime.now(), price, filePath, productInven);


            // DTO에서 엔터티로 변환
            ProductEntity uploadedFile = new ProductEntity();
            uploadedFile.createProduct(fileUploadDTO);


            // 엔터티 저장
            productRepository.save(uploadedFile);


            return "파일 업로드 성공!\n" + "상품이름: " + produtName + ", 상품가격: " + price + ", 사용자: " + userName;
        } catch (IOException e) {
            return "Failed to upload file!";
        }
    }

    public List<ProductDTO> productList(){      // 상품 리스트 반환하기
        List<ProductEntity> fileEntityList = productRepository.findAll();
        List<ProductDTO> fileDTOList = new ArrayList<>();

        for (ProductEntity productEntity : fileEntityList){
            ProductDTO fileUploadDTO = ProductDTO.toFileDTO(productEntity);

//            String imageURL = "/photos/postPhoto/" + productEntity.getProductName(); // 상대 경로를 생성합니다.
//            System.out.println("imageURL = " + imageURL);
//            fileUploadDTO.setProductURL(imageURL);
            fileDTOList.add(fileUploadDTO);
//            fileUploadDTO.setProductURL(uploadDir + "/" + uploadedFileEntity.getId());

        }
        return fileDTOList;
    }

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

    public List<ProductDTO> findTopView() {
        List<ProductEntity> top3Product = productRepository.findTop3ByProductView();

            List<ProductDTO> top3ProductDto = new ArrayList<>();

            for (ProductEntity productEntity : top3Product) {
                top3ProductDto.add(new ProductDTO(productEntity));
            }

            return top3ProductDto;

    }

}
