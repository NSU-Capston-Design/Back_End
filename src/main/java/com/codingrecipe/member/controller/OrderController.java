package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.OrderDTO;
import com.codingrecipe.member.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@AllArgsConstructor
@Controller
public class OrderController {
    private OrderService service;

    // 주문 처리 성공 화면으로 forward
    @PostMapping("/order/new")
    public String order(Integer addressNo, Principal principal, HttpSession session, RedirectAttributes ra) {
        OrderDTO.View dto = (OrderDTO.View)session.getAttribute("dto");
        service.add(addressNo, dto, principal.getName());
        ra.addFlashAttribute("isNew", true);
        return "redirect:/order/result";
    }

    // 주문 처리 성공 화면
    @GetMapping("/order/result")
    public String result(HttpServletRequest request) {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap == null)
            return "redirect:/";
        return "order/result";
    }
}

