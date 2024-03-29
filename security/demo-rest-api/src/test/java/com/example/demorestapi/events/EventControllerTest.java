package com.example.demorestapi.events;

import com.example.demorestapi.common.BaseControllerTest;
import com.example.demorestapi.common.RestDocsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {

    @Autowired
    EventRepository eventRepository;

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
                .andDo(document("create-event",
                        links( // 링크 문서화
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query events"),
                                linkWithRel("update-event").description("link to update event")
                        ), requestHeaders( // 요청헤더 문서화
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                        ), requestFields( // 요청필드 문서화
                                fieldWithPath("name").description("생성될 이벤트의 이름"),
                                fieldWithPath("description").description("생성될 이벤트의 설명"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작 날짜"),
                                fieldWithPath("closeEnrollmentDateTime").description("이벤트 등록 마감 날짜"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작 날짜"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료 날짜"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("basePrice").description("base 가격"),
                                fieldWithPath("maxPrice").description("max 가격"),
                                fieldWithPath("limitOfEnrollment").description("이벤트 인원 제한 수")
                        ), responseHeaders( // 응답헤더 문서화
                                headerWithName(HttpHeaders.LOCATION).description("응답 헤더"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("컨텐츠 타입")
                        ), relaxedResponseFields( // 응답필드 문서화
                                fieldWithPath("id").description("생성된 이벤트 id"),
                                fieldWithPath("name").description("이벤트의 이름"),
                                fieldWithPath("description").description("생성된 이벤트의 설명"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작 날짜"),
                                fieldWithPath("closeEnrollmentDateTime").description("이벤트 등록 마감 날짜"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작 날짜"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료 날짜"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("basePrice").description("base 가격"),
                                fieldWithPath("maxPrice").description("max 가격"),
                                fieldWithPath("limitOfEnrollment").description("이벤트 인원 제한 수"),
                                fieldWithPath("offline").description("오프라인 여부"),
                                fieldWithPath("free").description("무료 여부"),
                                fieldWithPath("eventStatus").description("이벤트 상태")
                        )
                ));


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
                .andDo(print())
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

    @Test
    @DisplayName("30개의 이벤트를 10개씩 조회 - 두번째 페이지 조회하기")
    void 이벤트_페이징_조회() throws Exception {
        // Given
        generateEvents(30);
        // When
        mockMvc.perform(get("/api/events")
                        .param("page", "1")
                        .param("sort", "name,DESC")
                        .param("size", "10")) // 두번쨰 페이지 조회
                .andDo(print())
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageable").exists())
                .andExpect(jsonPath("pageable.pageNumber").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(10))
                .andDo(document("query-events",
                        requestParameters(
                                parameterWithName("page").description("찾아올 페이지"),
                                parameterWithName("sort").description("페이지 정렬 기준. 정렬할 필드명과 정렬 순서를 ',' 로 구분하여 요청"),
                                parameterWithName("size").description("페이지당 아이템 개수")
                        )))
        ;
    }

    @Test
    @DisplayName("이벤트를 단건 조회한다")
    void 이벤트_단건조회() throws Exception {
        generateEvents(3);
        mockMvc.perform(get("/api/events/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andDo(document("get-an-event"))
        ;
    }

    @Test
    @Description("없는 이벤트를 조회했을 때 404 응답받기")
    void 없는이벤트_단건조회_404() throws Exception {
        generateEvents(3);
        mockMvc.perform(get("/api/events/100000"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("이벤트를 수정한다")
    void 이벤트_수정() throws Exception {
        Event event = generateOneEvent("Spring study");
        String eventName = "Advanced Spring study";
        EventDto eventDto = EventDto.builder()
                .name(eventName)
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

        mockMvc.perform(put("/api/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(eventName))
                .andDo(document("update-event"))
        ;
    }

    private void generateEvents(int num) {
        IntStream.range(0, num).forEach(i -> {
            Event event = new Event();
            event.setName("spring study " + i);
            eventRepository.save(event);
        });
    }

    private Event generateOneEvent(String name) {
        Event event = Event.builder().name(name).build();
        return eventRepository.save(event);
    }


}
