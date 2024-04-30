package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.member = :member")
    List<OrderEntity> findByMember(MemberEntity member);
}