package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.post.RequestPostDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    private String postTitle;

    private String postDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OrderBy("commentId desc")
    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    public Post(String postTitle, String postDetail, MemberEntity member) {
        this.postTitle = postTitle;
        this.postDetail = postDetail;
        this.member = member;
    }

    public Post(Long postId, RequestPostDTO requestPostDTO){

        this.postTitle = requestPostDTO.getPostTitle();
        this.postDetail = requestPostDTO.getPostDetail();
    }
}
