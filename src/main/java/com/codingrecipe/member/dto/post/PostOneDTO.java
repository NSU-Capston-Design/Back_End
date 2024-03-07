package com.codingrecipe.member.dto.post;

import com.codingrecipe.member.entity.Comment;
import com.codingrecipe.member.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostOneDTO {

    private Long postId;

    private String postTitle;

    private String postDetail;

    private String userId;

    private List<Comment> comments;

    public PostOneDTO(Post post) {
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postDetail = post.getPostDetail();
        this.userId = post.getMember().getUserId();
        this.comments = post.getCommentList();
    }
}
