package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/user/register")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/user/register")    // user/register
    public String save(@RequestBody MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/user/login")            //user/login
    public String login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("userId", loginResult.getUserId());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/user/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    } // 관리자모드 회원목록

    @GetMapping("/user/{id}")     // 아이디로 마이페이지 찾기? user/{id}
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/user/update")   // user/update  이메일로 하는걸 아이디로 바꿔라
    public String updateForm(HttpSession session, Model model) {
        String myuserId = (String) session.getAttribute("loginuserId");
        MemberDTO memberDTO = memberService.updateForm(myuserId);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/user/update")  //업데이트 된 내용으로 이루어진 멤버페이지를 보여줌
    public String update(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/user/" + memberDTO.getId();
    }

    @GetMapping("/user/delete/{id}")  //user/delete/{id} 지금 id는 인덱스값이라 현재 erd 테이블에 없음 추가하는걸로
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/user/";
    }

    @GetMapping("/user/logout")   // user/logout 세션저장 방법
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @PostMapping("/user/userid-check")     //user/user_id-check
    public @ResponseBody String idCheck(@RequestBody String userid) {
        System.out.println("userid = " + userid);
        String checkResult = memberService.idCheck(userid);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
    }

}

