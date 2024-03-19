package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByAdminId(Long adminId);
}
