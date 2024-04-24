package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT p FROM ProductEntity p ORDER BY p.productView DESC")
    Optional<List<ProductEntity>> findTop3ByProductView();

    @Query(value = "SELECT p FROM ProductEntity p where p.fileId = :fileId")
    Optional<ProductEntity> findByFileId(Long fileId);

    @Query(value = "select p from ProductEntity p join fetch p.memberEntity")
    Optional<List<ProductEntity>> findByMemberEntityOfProductEntity();

    @Query(value = "select p from ProductEntity p where p.fileId IN :productId")
    List<ProductEntity> findAllByProductId(long[] productId);
}
