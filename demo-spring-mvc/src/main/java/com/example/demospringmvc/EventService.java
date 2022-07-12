package com.example.demospringmvc;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    public List<Event> getEvents(){
        Event event1 = Event.builder()
                .name("스프링 웹 MVC 스터디 1차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2022, 4, 26, 7, 30))
                .endDateTime(LocalDateTime.of(2022, 4, 26, 10, 30))
                .build();
        Event event2 = Event.builder()
                .name("스프링 웹 MVC 스터디 2차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2022, 5, 26, 7, 30))
                .endDateTime(LocalDateTime.of(2022, 5, 26, 10, 30))
                .build();

        return List.of(event1, event2);
    }
}
