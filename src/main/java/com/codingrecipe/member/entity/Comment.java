package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.comment.CommentSaveDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(MemberEntity memberEntity, Post post, CommentSaveDTO saveDTO) {
        this.comment = saveDTO.getComment();
        this.memberEntity = memberEntity;
        this.post = post;
    }
}
