package com.codingrecipe.member.entity.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum DeliveryStatus {
    BEFO,         // 배송 전
    SHIP,         // 배송 중
    COMP          // 배송 완료
}
