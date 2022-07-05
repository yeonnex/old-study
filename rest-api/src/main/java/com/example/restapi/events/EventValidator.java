package com.example.restapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {
        if ((eventDto.getBasePrice() > eventDto.getMaxPrice()) && eventDto.getMaxPrice() > 0) { // 무제한 경매가 아닌데도 Base 가 더 큰 말이 안되는 경우

            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong!"); // fiend error
            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong!");
        }


        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        // endEventDateTime 값 검증
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getEndEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.reject("wrongValue", "endEventDateTime is wrong!"); // global error
        }
        // TODO BeginEventDateTime
        // TODO EndEnrollmentDateTime
    }
}
