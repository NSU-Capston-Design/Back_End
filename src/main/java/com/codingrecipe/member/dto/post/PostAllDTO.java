package com.codingrecipe.member.dto.post;

import com.codingrecipe.member.entity.Post;
import lombok.Data;

@Data
public class PostAllDTO {

    private Long postId;

    private String postTitle;

    private String postDetail;

    private String userId;

    private int commentCount;

    public PostAllDTO(Post post) {
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postDetail = post.getPostDetail();
        this.userId = post.getMember().getUserId();
        this.commentCount = post.getCommentList().size();
    }
}
