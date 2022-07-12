package com.example.demospringmvc;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    private String name;
    private LocalDateTime startDateTime;
    private  LocalDateTime endDateTime;
    private Integer limitOfEnrollment;
}
