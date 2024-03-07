package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.comment.CommentDTO;
import com.codingrecipe.member.dto.comment.CommentSaveDTO;
import com.codingrecipe.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 저장
     */
    @PostMapping("/save")
    public ResponseEntity<String> commentSave(@RequestBody CommentSaveDTO commentSaveDTO){
        String s = commentService.commentSave(commentSaveDTO);
        if (s.equals("ok")){
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 게시글에 달린 댓글 조회
     */
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> commentAll(@PathVariable Integer postId){
        List<CommentDTO> commentDTOs = commentService.commentAll(postId);
        if (commentDTOs != null){
            return ResponseEntity.ok(commentDTOs);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable Integer commentId){
        String s = commentService.commentDelete(commentId);
        if (s.equals("ok")){
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
