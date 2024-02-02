package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.event.EventList;
import com.codingrecipe.member.dto.event.EventUpload;
import com.codingrecipe.member.dto.product.ProductDetail;
import com.codingrecipe.member.exception.NotFoundEventException;
import com.codingrecipe.member.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * @return 이벤트 전체 출력
     */
    @GetMapping("/event")
    public ResponseEntity<List<EventList>> eventAll(){
        List<EventList> eventLists = eventService.findAll();
        if (eventLists == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(eventLists);
        }
    }


    /**
     * @return 이벤트 단일 출력 (eventId 기반)
     */
    @GetMapping("/event/detail")
    public ResponseEntity<EventList> eventDetail(@RequestParam(name = "eventId") String eventId){
        try {

            long id = Long.parseLong(eventId);    // useParams인한 String을 Long으로 변환
            EventList event = eventService.findById(id);
            return ResponseEntity.ok(event);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 이벤트 업로드
     * 이벤트 종료 날짜는 문자열로 받을것.
     * value를 가져와서, new Date(value).toISOString(); 하면 될듯.
     */
    @PostMapping("/event/upload")
    public ResponseEntity<String> eventUpload(@RequestPart(name = "event_file") MultipartFile file,
                                              String title, String detail, String end) throws NotFoundEventException {
        try {
            System.out.println("file = " + file);
            System.out.println("title = " + title);
            EventUpload upload = new EventUpload();
            upload.setFile(file);
            upload.setEventTitle(title);
            upload.setEventDetail(detail);
            upload.setEventEnd(end);

            eventService.eventUpload(upload);

            return ResponseEntity.ok("이벤트 업로드 성공");
        } catch (NotFoundEventException e){
            throw new NotFoundEventException();
        }
    }
}
