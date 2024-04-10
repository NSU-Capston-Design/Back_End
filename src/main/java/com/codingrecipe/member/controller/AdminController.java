package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.admin.AdminDto;
import com.codingrecipe.member.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    /**
     * 관리자 계정 로그인
     * @param adminDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminDto adminDto, HttpServletRequest request){
        try {
            log.info("=== 관리자 로그인 시도 ===");
            String s = adminService.adminLogin(adminDto);
            if (s.equals("ok")){
                log.info("=== 세션 생성 ===");
                HttpSession session = request.getSession();
                session.setAttribute("adminId", adminDto.getAdminId());

                return ResponseEntity.ok()
                        .header("Set-Cookie", "sessionId="+ session.getId())
                        .body(adminDto.getAdminId());

            } else {
                log.info("=== 존재하지 않는 관리자 계정 ===");
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            log.info("=== 비밀번호가 틀림 ===");
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
