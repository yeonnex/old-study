package com.example.demorestapi.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {

    @Test
    void builder() {
        Event event = Event.builder()
                .name("spring rest api")
                .description("rest api with spring boot")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    void javaBean() {
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

    @ParameterizedTest
    @CsvSource({
            "0, 0, true",
            "100, 0, false",
            "0, 100, false"
    })
    @DisplayName("basePrice 와 maxPrice 값에 따라 free 여부 변경")
    void testFree(int basePrice, int maxPrice, boolean isFree) {
    // Given
    Event event = Event.builder()
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .build();
    // When
    event.update();
    // Then
    assertThat(event.isFree()).isEqualTo(isFree);
    }

    @Test
    @DisplayName("location 값에 따라 offline 여부 결정")
    void testOffline(){
        Event event = Event.builder()
                .location("강남구").build();
        event.update();
        assertThat(event.isOffline()).isTrue();

        Event event1 = Event.builder()
                .location(null).build();
        event1.update();
        assertThat(event1.isOffline()).isFalse();
    }

}