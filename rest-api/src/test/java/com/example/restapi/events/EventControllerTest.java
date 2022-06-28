package com.example.restapi.events;

import com.example.restapi.common.RestDocsConfiguration;
import com.example.restapi.common.TestDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hibernate.event.spi.EvictEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("이벤트 생성하기")
    @TestDescription("이벤트 생성하기")
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
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.PUBLISHED)))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andExpect(jsonPath("_links.profile").exists())

                // RestDocs 문서 생성
                .andDo(document("create-event", // 요청 본문, 응답 본문 문서화
                  links( // 링크 문서화
                                        linkWithRel("self").description("link to self"),
                                                    linkWithRel("query-events").description("link to event list"),
                                                    linkWithRel("update-event").description("link to update event"),
                                                    linkWithRel("profile").description("link to profile")
                                ),
                            requestHeaders( // 요청 헤더 문서화
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type"),
                                               headerWithName(HttpHeaders.ACCEPT).description("accept header")

                            ),
                            requestFields( // 요청 필드 문서화
                                    fieldWithPath("name").description("name of event"),
                                    fieldWithPath("description").description("description of event"),
                                    fieldWithPath("beginEnrollmentDateTime").description("begin date time of event enrollment"),
                                    fieldWithPath("endEnrollmentDateTime").description("end date time of event enrollment"),
                                    fieldWithPath("beginEventDateTime").description("begin date time of event"),
                                    fieldWithPath("endEventDateTime").description("end date time of event"),
                                    fieldWithPath("location").description("location of event"),
                                    fieldWithPath("basePrice").description("base price of event"),
                                    fieldWithPath("maxPrice").description("max price of event"),
                                    fieldWithPath("limitOfEnrollment").description("limit number of event enrollment")
                            ),
                            responseHeaders( // 응답 헤더 문서화
                                        headerWithName(HttpHeaders.LOCATION).description("location"),
                                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                            ),
                            responseFields( // 응답 필드 문서화
                                    fieldWithPath("id").description("id of new event"),
                                    fieldWithPath("name").description("name of new event"),
                                    fieldWithPath("description").description("description of new event"),
                                    fieldWithPath("beginEnrollmentDateTime").description("begin date time of new event enrollment"),
                                    fieldWithPath("endEnrollmentDateTime").description("end date time of new event enrollment"),
                                    fieldWithPath("beginEventDateTime").description("begin date time of new event"),
                                    fieldWithPath("endEventDateTime").description("end date time of new event"),
                                    fieldWithPath("location").description("location of new event"),
                                    fieldWithPath("basePrice").description("base price of new event"),
                                    fieldWithPath("maxPrice").description("max price of new event"),
                                    fieldWithPath("limitOfEnrollment").description("limit number of new event enrollment"),
                                    fieldWithPath("offline").description("whether offline or not"),
                                    fieldWithPath("free").description("whether free or not"),
                                    fieldWithPath("eventStatus").description("status of new event"),
                                    fieldWithPath("_links.self.href").description("link to self"),
                                    fieldWithPath("_links.query-events.href").description("link to event list"),
                                    fieldWithPath("_links.update-event.href").description("link to update event"),
                                    fieldWithPath("_links.profile.href").description("link to profile")
                            )

                ))

        ;
    }
    @Test
    @DisplayName("입력값 이외의 값이 들어왔을 때 예외 발생시키기")
    void createEvent_Bad_Request() throws Exception {
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

    @Test
    @DisplayName("에러 응답에 에러 정보 담아 응답하기")
    void createEvent_Bad_Request_Return_ErrorMsg() throws Exception {
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].field").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("errors[0].rejectedValue").exists())
        ;

    }

    @Test
    @DisplayName("30개의 이벤트를 한 페이지당 10개씩 뿌려주는 테스트")
    void queryEvents() throws Exception {
        List<Event> events = new ArrayList<>();

        IntStream.range(0, 30).forEach(i -> {
            Event event = generateEvent(i);
            events.add(event);
        });

        List<Event> eventList = eventRepository.saveAll(events);

        this.mockMvc.perform(get("/api/events")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageable").exists())
        ;


    }

    private Event generateEvent(int i) {
        return Event.builder()
                .name("Spring " + i)
                .description("REST API with Spring")
                .build();
    }

}
