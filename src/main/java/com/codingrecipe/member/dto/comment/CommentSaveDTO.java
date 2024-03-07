package com.codingrecipe.member.dto.comment;

import lombok.Data;

@Data
public class CommentSaveDTO {
    private String comment;
    private String userId;
    private Integer postId;
}
