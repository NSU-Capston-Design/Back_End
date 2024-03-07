package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.member.MemberDTO;
import com.codingrecipe.member.dto.member.UserIdDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/user/register")
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO) {
        String save = memberService.save(memberDTO);

        return ResponseEntity.ok("회원가입 성공 \n" + "아이디: " + save);
    }

    @PostMapping("/user/login")            //user/login
    public ResponseEntity<Integer> login(@RequestBody MemberDTO memberDTO, HttpServletRequest request) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공시 세션 부여
            HttpSession session = request.getSession();  // 세션 생성
            session.setAttribute("userId", loginResult.getUserId());
            System.out.println("session = " + session);

            System.out.println("loginResult = " + loginResult.getMemberId());

            Long longValue = loginResult.getMemberId();
            int memberId = longValue.intValue();

            System.out.println("memberId = " + memberId);

            return ResponseEntity.ok(memberId);
        } else {
            // login 실패
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/user/logout")   // user/logout 세션 삭제
    public ResponseEntity<?> logout(HttpServletRequest request) {   // <?> 제네릭 와일드카드
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
            return ResponseEntity.ok("로그아웃 및 세션삭제");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/users")        // 관리자모드용 회원목록
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> memberDTOList = memberService.findAll();
        if (memberDTOList != null) {
            return ResponseEntity.ok(memberDTOList);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user/{id}")     // 마이페이지
    public ResponseEntity<MemberDTO> findById(@PathVariable(name = "id") int id) {
        MemberDTO memberDTO = memberService.findById((long)id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/user")    // 회원정보 업데이트 폼(원래 있던 데이터만 꺼내줌)
    public ResponseEntity<MemberDTO> updateForm(@RequestBody UserIdDTO userIdDTO) {
        MemberDTO memberDTO = memberService.updateForm(userIdDTO.getUserId());
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/update")  //업데이트 된 내용으로 이루어진 멤버페이지를 보여줌
    public ResponseEntity<String> update(@RequestBody MemberDTO memberDTO) {
        try {
            memberService.update(memberDTO);
            return ResponseEntity.ok("수정 완료");
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/delete/{userId}")  //user/delete/{id} 지금 id는 인덱스값이라 현재 erd 테이블에 없음 추가하는걸로
    public ResponseEntity<String> deleteById(@PathVariable("userId") String userId) {
        memberService.deleteById(userId);
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/user/idcheck")     //user/user_id-check
    public ResponseEntity<String> idCheck(@RequestParam("userId") String userid) {
        System.out.println("userid = " + userid);
        String checkResult = memberService.idCheck(userid);
        if (checkResult != null){
            return ResponseEntity.ok(checkResult);
        } return ResponseEntity.badRequest().build();
    }

}

