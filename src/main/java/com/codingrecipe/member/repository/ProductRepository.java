package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // 추가적인 쿼리 메소드가 필요한 경우 작성할 수 있습니다.

    @Query(value = "SELECT p FROM ProductEntity p ORDER BY p.productView DESC")
    List<ProductEntity> findTop3ByProductView();

    @Query(value = "select p from ProductEntity p join fetch p.memberEntity")
    List<ProductEntity> findByMemberEntityOfProductEntity();

    @Query(value = "select p from ProductEntity p where p.fileId IN :productId")
    List<ProductEntity> findAllByProductId(long[] productId);
}
