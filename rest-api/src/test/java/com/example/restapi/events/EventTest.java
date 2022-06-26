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

    @Test
    @DisplayName("offline 과 free 값 업데이트하여 엔티티 필드에 비즈니스 로직 적용")
    void updateBizEvent() {

        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();
        // When
        event.update();

        // Then
        assertThat(event.isFree()).isTrue();

        //Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isFalse();

        //Given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isFalse();

        // Given
        event = Event.builder()
                .location("낙성대 오렌지 연필")
                .build();
        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isTrue();

    }
}