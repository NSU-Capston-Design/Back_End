package com.codingrecipe.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 조회수에 따라 상품을 3개 가져오는 컨트롤러, 서비스 작성.
     */
}
