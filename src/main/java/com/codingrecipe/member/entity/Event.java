package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.event.EventList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;               // 이벤트 pk값

    private String eventTitle;          // 이벤트명

    private String eventDetail;         // 이벤트 상세

    private String eventImg;            // 이벤트 이미지 주소

    private String eventData;           // 이벤트 업로드 시간

    private String eventEnd;            // 이벤트 종료 시간

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Event(String eventTitle, String eventDetail, String eventImg, String eventData, String eventEnd, Admin admin) {
        this.eventTitle = eventTitle;
        this.eventDetail = eventDetail;
        this.eventImg = eventImg;
        this.eventData = eventData;
        this.eventEnd = eventEnd;
        this.admin = admin;
    }

}
