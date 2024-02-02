package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.event.EventList;
import com.codingrecipe.member.dto.event.EventUpload;
import com.codingrecipe.member.entity.Event;
import com.codingrecipe.member.exception.NotFoundEventException;
import com.codingrecipe.member.repository.EventRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Value("${upload.dir}" + "eventPhoto")
    private String uploadDir;


    public EventList findById(Long id){
        Optional<Event> byId = eventRepository.findById(id);
        EventList event = new EventList();
        if (byId.isPresent()){
            event = EventList.toEventList(byId.get());
            return event;
        } else {
            return null;
        }
    }

    public List<EventList> findAll(){
        List<Event> all = eventRepository.findAll();
        if (all.isEmpty()){
            return null;
        }else {
            List<EventList> eventLists = new ArrayList<>();
            for (Event event : all) {
                eventLists.add(EventList.toEventList(event));
            }

            return eventLists;
        }
    }

    public void eventUpload(EventUpload eventUpload) throws NotFoundEventException {

        try {

            String eventTitle = eventUpload.getEventTitle();
            String eventDetail = eventUpload.getEventDetail();
            String eventE = eventUpload.getEventEnd();
            String eventEnd = "default";                        // event종료날짜 default 넣기
            if (eventE == null){
                 eventEnd = "종료기한없음";
            }
            else {
                 eventEnd = formatDate(eventE);       // 날짜 이쁘게 변환
            }

            String uuid = UUID.randomUUID().toString(); // uuid 생성
            MultipartFile file = eventUpload.getFile();
            String filePath = uploadDir + File.separator + uuid;
            System.out.println(" 여기는 되나 ???");
            File dest = new File(filePath);
            file.transferTo(dest);                          // 실제 경로에 저장

            Date currentDate = new Date(); // 현재 시간
            SimpleDateFormat format = new SimpleDateFormat("yy년/MM월/dd일 - HH:mm"); // 형식 변환
            String date = format.format(currentDate);   // 변환한 날짜 저장

            filePath = "/photos/eventPhoto/" + uuid;                     // DB 저장경로로 변경
            System.out.println("date = " + date + "12: " + filePath);
            Event event = new Event(eventTitle, eventDetail, filePath, date, eventEnd);
            eventRepository.save(event);    // 저장
        } catch (Exception e){
            System.out.println("오류 !.. 발...생!! ");
            throw new NotFoundEventException("이벤트를 찾을 수 없습니다.");
        }
    }

    private static String formatDate(String eventEnd){
        Instant instant = Instant.parse(eventEnd);
        Date date = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat("yy년/MM월/dd일 - HH:mm");
        return format.format(date);
    }
}
