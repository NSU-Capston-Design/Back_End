package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.comment.CommentDTO;
import com.codingrecipe.member.dto.comment.CommentSaveDTO;
import com.codingrecipe.member.entity.Comment;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.entity.Post;
import com.codingrecipe.member.repository.CommentRepository;
import com.codingrecipe.member.repository.MemberRepository;
import com.codingrecipe.member.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 저장
     */
    public String commentSave(CommentSaveDTO commentSaveDTO){
        Optional<MemberEntity> byUserId = memberRepository.findByUserId(commentSaveDTO.getUserId());
        Optional<Post> byId = postRepository.findById((long) commentSaveDTO.getPostId());
        if (byId.isPresent() && byUserId.isPresent()){
            Comment comment = new Comment(byUserId.get(), byId.get(), commentSaveDTO);
            commentRepository.save(comment);
            return "ok";
        } else {
            return null;
        }
    }

    /**
     * 댓글 조회
     */
    public List<CommentDTO> commentAll(Integer postId){
        Optional<Post> byId = postRepository.findById((long) postId);
        if (byId.isPresent()){
            List<Comment> commentByPostId = commentRepository.findCommentByPostId((long)postId);
            List<CommentDTO> commentDTOs = new ArrayList<>();

            for (Comment comment : commentByPostId){
                CommentDTO commentDTO = new CommentDTO(comment);

                commentDTOs.add(commentDTO);
            }
            return commentDTOs;
        } else {
            return null;
        }
    }

    /**
     * 댓글 삭제
     */
    public String commentDelete(Integer commentId){
        commentRepository.deleteById((long)commentId);
        return "ok";
    }
}
