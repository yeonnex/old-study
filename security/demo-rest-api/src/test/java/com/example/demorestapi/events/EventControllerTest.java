package com.example.demorestapi.events;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("정상적으로 이벤트를 생성")
    void createEvent() throws Exception {

        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 4, 6, 5, 4, 3))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 5, 3, 3, 3, 3, 3))
                .beginEventDateTime(LocalDateTime.of(2022, 6, 7, 1, 1, 1, 1))
                .endEventDateTime(LocalDateTime.of(2023, 4, 4, 4, 4, 4))
                .location("강남역")
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(30)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
        ;
    }

    @Test
    @DisplayName("입력받을 수 없는 값을 사용한 경우 에러가 발생하는 테스트")
    void createEvent_Bad_Request() throws Exception {

        Event event = Event.builder()
                .id(316)
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.now())
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 2, 2, 6, 7, 12))
                .beginEventDateTime(LocalDateTime.of(2023, 4, 4, 6, 7))
                .endEventDateTime(LocalDateTime.of(2024, 4, 4, 6, 7))
                .basePrice(100)
                .maxPrice(200)
                .free(true)
                .offline(true)
                .eventStatus(EventStatus.PUBLISHED).build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력값이 비어있는 경우 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .build();
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 비즈니스 로직상 잘못된 경우 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 4, 6, 5, 4, 3))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 5, 3, 3, 3, 3, 3))
                .beginEventDateTime(LocalDateTime.of(2023, 6, 7, 1, 1, 1, 1))
                .endEventDateTime(LocalDateTime.of(2020, 4, 4, 4, 4, 4))
                .location("강남역")
                .basePrice(200)
                .maxPrice(100)
                .limitOfEnrollment(30)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 이상한 경우 Bad Request 로 응답")
    void createEvent_Bad_Request_Response() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 4, 6, 5, 4, 3))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 5, 3, 3, 3, 3, 3))
                .beginEventDateTime(LocalDateTime.of(2023, 6, 7, 1, 1, 1, 1))
                .endEventDateTime(LocalDateTime.of(2020, 4, 4, 4, 4, 4))
                .location("강남역")
                .basePrice(200)
                .maxPrice(100)
                .limitOfEnrollment(30)
                .build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;
    }

    @Test
    @DisplayName("basePrice 와 maxPrice 에 따라 free 여부 달라지는 비즈니스 로직 테스트")
    void testFree() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2021, 4, 6, 5, 4, 3))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 5, 3, 3, 3, 3, 3))
                .beginEventDateTime(LocalDateTime.of(2022, 6, 7, 1, 1, 1, 1))
                .endEventDateTime(LocalDateTime.of(2023, 4, 4, 4, 4, 4))
                .location("강남역")
                .basePrice(0)
                .maxPrice(0)
                .limitOfEnrollment(30)
                .build();
        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto))
        ).andExpect(jsonPath("free").value(Matchers.is(true)));
    }


}
