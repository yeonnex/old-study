package com.example.demorestapi.events;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {

    @Test
    void builder(){
        Event event = Event.builder()
                .name("spring rest api")
                .description("rest api with spring boot")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    void javaBean(){
        // GIven
        String name = "spring rest api";
        String description = "rest api with spring boot";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

}