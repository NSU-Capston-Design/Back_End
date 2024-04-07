package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.member.UserIdDTO;
import com.codingrecipe.member.dto.post.PostAllDTO;
import com.codingrecipe.member.dto.post.PostOneDTO;
import com.codingrecipe.member.dto.post.PostSaveDTO;
import com.codingrecipe.member.dto.post.RequestPostDTO;
import com.codingrecipe.member.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 저장
     */
    @PostMapping("/save")
    public ResponseEntity<String> postSave(@RequestBody PostSaveDTO postSaveDTO){
        String s = postService.postSave(postSaveDTO);
        if (s.equals("ok")){
            return ResponseEntity.ok("ok");
        } return ResponseEntity.notFound().build();
    }

    /**
     * 전체 게시글 출력
     */
    @GetMapping("/all")
    public ResponseEntity<List<PostAllDTO>> postAll(){
        List<PostAllDTO> postAllDTOs = postService.postAll();
        if (postAllDTOs.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postAllDTOs);
    }

    /**
     * 단일 게시글 조회
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostOneDTO> postOne(@PathVariable(name = "postId") Integer postId){
        Long post = (long) postId;
        PostOneDTO postOneDTO = postService.postOne(post);
        if (postOneDTO != null){
            return ResponseEntity.ok(postOneDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @return 게시글 수정
     */
    @PostMapping("/{postId}")
    public ResponseEntity<String> postModify(@PathVariable Integer postId, @RequestBody RequestPostDTO requestPostDTO){
        long post = postId;
        String s = postService.postModify(post, requestPostDTO);
        if (s.equals("ok")){
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> postDelete(@RequestBody Integer postId){
        long post = postId;
        String s = postService.postDelete(post);
        if (s.equals("ok")){
            return ResponseEntity.ok("ok");
        } else {
            return null;
        }
    }
}
