package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.member.MemberDTO;
import com.codingrecipe.member.dto.product.ProductDTO;
import com.codingrecipe.member.dto.product.ProductDetail;
import com.codingrecipe.member.dto.product.ProductListDTO;
import com.codingrecipe.member.dto.product.ProductRequest;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.ProductEntity;
import com.codingrecipe.member.exception.NotFoundMemberException;
import com.codingrecipe.member.exception.NotFoundProductException;
import com.codingrecipe.member.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
    private final MemberService memberService;

//    @Autowired
//    private MemberRepository memberRepository;


    @Value("${upload.dir}")
    private String uploadDir;



    public String uploadFile(ProductRequest productRequest) {          // 파일을 매개변수로 받음
        try {
            MultipartFile file = productRequest.getFile();
            String produtName = productRequest.getData().getProductName();
            int price = productRequest.getData().getProductPrice();
            int productInven = productRequest.getData().getProductInven();
            MemberEntity byMemberId = memberService.findByMemberId(productRequest.getData().getMemberId()).get();

            String originProductName = produtName;
            String replaceProductName = originProductName.replaceAll("\\s", "_");   // 공백을 언더바로 치환

            String filePath = uploadDir + File.separator + replaceProductName;   // 파일 경로 설정 (uploadDir에는 static 포토주소 있음)
            File dest = new File(filePath);         // 파일 객체 dest를 생성 하고, 파일 경로를 넣음
            file.transferTo(dest);    // transferTo 메서드는 MultipartFile에서 실제 파일로의 이동 또는 복사를 수행하는 메서드. (절대경로에 저장)

            filePath = "/photos/postPhoto/" + replaceProductName;   // 상대경로로 변경



            ProductDTO fileUploadDTO = new ProductDTO(produtName, file.getSize(),
                    file.getContentType(), LocalDateTime.now(), price, filePath, productInven, byMemberId);


            // DTO에서 엔터티로 변환
            ProductEntity uploadedFile = new ProductEntity();
            uploadedFile.createProduct(fileUploadDTO);


            // 엔터티 저장
            productRepository.save(uploadedFile);


            return "파일 업로드 성공!\n" + "상품이름: " + produtName + ", 상품가격: " + price + ", 사용자: " + byMemberId;
        } catch (IOException e) {
            return "Failed to upload file!";
        } catch (NotFoundMemberException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProductListDTO> productList(){      // 상품 리스트 반환하기
        List<ProductEntity> fileEntityList = productRepository.findAll();
        List<ProductListDTO> fileDTOList = new ArrayList<>();

        for (ProductEntity productEntity : fileEntityList){
            ProductListDTO fileUploadDTO = ProductListDTO.toListDTO(productEntity);

            fileDTOList.add(fileUploadDTO);
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

    public List<ProductDTO> findTopView() throws NotFoundProductException {
        List<ProductEntity> top3Product = productRepository.findTop3ByProductView();
        List<ProductDTO> top3ProductDto = new ArrayList<>();


        for (int i = 0; i < 3; i++) {   // 스프링 데이터 JPA가 작동 안해서, 수동으로 넣어둠.
            ProductEntity productEntity = top3Product.get(i);
            if(productEntity == null){
                throw new NotFoundProductException("상품이 없습니다.");
            }
            top3ProductDto.add(new ProductDTO(productEntity));
        }

        System.out.println("top3Product = " + top3Product);

        System.out.println("top3ProductDto = " + top3ProductDto);

        return top3ProductDto;

    }

}
