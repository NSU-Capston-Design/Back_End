package com.codingrecipe.member.dto.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class EventUpload {

    private MultipartFile file;         // 파일
    private String eventTitle;          // 이벤트명
    private String eventDetail;         // 이벤트 상세
    private String eventEnd;            // 이벤트 종료 시간
}
