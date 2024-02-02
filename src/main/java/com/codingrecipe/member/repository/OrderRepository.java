package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // 추가적인 메소드가 필요한 경우 작성.
}
