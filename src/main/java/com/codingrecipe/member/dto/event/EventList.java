package com.codingrecipe.member.dto.event;

import com.codingrecipe.member.entity.Event;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@RequiredArgsConstructor
public class EventList {

    private Long eventId;               // 이벤트 pk값

    private String eventTitle;          // 이벤트명

    private String eventDetail;         // 이벤트 상세

    private String eventImg;            // 이벤트 이미지 주소

    private String eventData;           // 이벤트 업로드 시간

    private String eventEnd;            // 이벤트 종료 시간

    public static EventList toEventList(Event event){
        EventList eventList = new EventList();
        eventList.setEventId(event.getEventId());
        eventList.setEventTitle(event.getEventTitle());
        eventList.setEventDetail(event.getEventDetail());
        eventList.setEventImg(event.getEventImg());
        eventList.setEventData(event.getEventData());
        eventList.setEventEnd(event.getEventEnd());

        return eventList;
    }
}
