package com.codingrecipe.member.dto.post;

import lombok.Data;

@Data
public class RequestPostDTO {

    private String postTitle;

    private String postDetail;

    private String userId;
}
