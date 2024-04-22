package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT p FROM ProductEntity p ORDER BY p.productView DESC")
    List<ProductEntity> findTop3ByProductView();

    @Query(value = "SELECT p FROM ProductEntity p where p.fileId = :fileId")
    ProductEntity findByFileId(Long fileId);

    @Query(value = "select p from ProductEntity p join fetch p.memberEntity")
    List<ProductEntity> findByMemberEntityOfProductEntity();

    @Query(value = "select p from ProductEntity p where p.fileId IN :productId")
    List<ProductEntity> findAllByProductId(long[] productId);
}
