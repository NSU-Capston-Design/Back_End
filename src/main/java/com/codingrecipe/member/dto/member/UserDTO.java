package com.codingrecipe.member.dto.member;

import lombok.Data;

@Data
public class UserDTO {
    int memberId;
    String userId;

    public UserDTO() {
    }

    public UserDTO(int memberId, String userId) {
        this.memberId = memberId;
        this.userId = userId;
    }
}
