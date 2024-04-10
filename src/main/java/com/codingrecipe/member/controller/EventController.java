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
    public ResponseEntity<EventList> eventDetail(@RequestParam(name = "eventId") int eventId){
        try {

            EventList event = eventService.findById((long) eventId);
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
                                              int id, String title, String detail, String end) throws NotFoundEventException {
        try {
            System.out.println("file = " + file);
            System.out.println("title = " + title);
            EventUpload upload = new EventUpload();
            upload.setFile(file);
            upload.setEventTitle(title);
            upload.setEventDetail(detail);
            upload.setEventEnd(end);
            upload.setAdminId((long) id);

            eventService.eventUpload(upload);

            return ResponseEntity.ok("이벤트 업로드 성공");
        } catch (NotFoundEventException e){
            throw new NotFoundEventException();
        }
    }

    /**
     * 이벤트 삭제
     */
    @DeleteMapping("/event/delete")
    public ResponseEntity<String> deleteEvent(@RequestBody int eventId){
        try {
            String s = eventService.deleteEvent(eventId);
            if (s.equals("ok")){
                return ResponseEntity.ok("ok");
            }
        }catch (NotFoundEventException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.notFound().build();
    }
}
