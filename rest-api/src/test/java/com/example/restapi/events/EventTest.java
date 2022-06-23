package com.example.restapi.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {
    @Test
    @DisplayName("빌더로 이벤트 생성")
    void builder(){
        Event event = Event.builder()
                .name("Spring REST API")
                .description("REST API with Spring")
                .build();
        Assertions.assertThat(event).isNotNull();
    }

    @Test
    @DisplayName("자바빈 스펙 준수 따라 이벤트 생성")
    void javaBean(){
        // Given
        String name = "REST API";
        String description = "스프링 REST API";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }
}