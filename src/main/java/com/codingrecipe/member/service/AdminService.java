package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.admin.AdminDto;
import com.codingrecipe.member.entity.Admin;
import com.codingrecipe.member.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public String adminLogin(AdminDto adminDto) throws Exception {
        Admin byAdminId = adminRepository.findByAdminId(adminDto.getAdminId());
        if (byAdminId != null){

            if (adminDto.getAdminPw().equals(byAdminId.getAdminPw())){
                return "ok";
            }else {
                throw new Exception("비밀번호 불일치");
            }

        }
        throw new Exception("존재하지 않는 관리자입니다.");
    }
}
