package com.codingrecipe.member.dto.comment;

import com.codingrecipe.member.entity.Comment;
import lombok.Data;

@Data
public class CommentDTO {
    private Long commentId;
    private String comment;
    private String userId;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.comment = comment.getComment();
        this.userId = comment.getMemberEntity().getUserId();
    }
}
