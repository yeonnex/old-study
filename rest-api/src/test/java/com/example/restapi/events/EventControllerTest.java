package com.example.restapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("이벤트 생성하기")
    void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 6, 23, 15, 56))
                .endEnrollmentDateTime(LocalDateTime.of(2022, 6, 24, 15, 56))
                .beginEventDateTime(LocalDateTime.of(2022, 6, 25, 12, 30))
                .endEventDateTime(LocalDateTime.of(2022, 6, 26, 12, 30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))
                        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("description").value("REST API with Spring"))
                .andExpect(jsonPath("id").value(Matchers.not(200))) // TODO 200L 로 하면 매칭되지 않음. 즉 반환되는 값은 Long 이 아니라 Integer 인 것...
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.PUBLISHED)))
        ;
    }
    @Test
    @DisplayName("입력값 이외의 값이 들어왔을 때 예외 발생시키기")
    void createEvent_Bad_Request() throws Exception{
        Event event = Event.builder()
                .id(200L)
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 6, 23, 15, 56))
                .endEventDateTime(LocalDateTime.of(2022, 6, 24, 15, 56))
                .beginEventDateTime(LocalDateTime.of(2022, 6, 25, 12, 30))
                .endEventDateTime(LocalDateTime.of(2022, 6, 26, 12, 30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())

        ;
    }

    @Test
    @DisplayName("입력값 없이 이벤트 생성 요청시 Bad Request 응답")
    void createEvent_Bad_Request_Empty_Input() throws  Exception{
        EventDto eventDto = EventDto.builder().build();
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("비즈니스 로직상으로 이상한 입력 요청")
    void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 6, 24, 15, 56))
                .endEnrollmentDateTime(LocalDateTime.of(2022, 6, 23, 15, 56))
                .beginEventDateTime(LocalDateTime.of(2022, 6, 26, 12, 30))
                .endEventDateTime(LocalDateTime.of(2022, 6, 25, 12, 30))
                .basePrice(1000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(eventDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }



}
