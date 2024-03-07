package com.codingrecipe.member.dto.post;

import com.codingrecipe.member.entity.MemberEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class PostSaveDTO {

    private String postTitle;

    private String postDetail;

    private String userId;
}
