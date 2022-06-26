package com.example.restapi.events;

import com.example.restapi.common.TestDescription;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EventTest{

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

    @ParameterizedTest
    @MethodSource("parameterProvider")
    @TestDescription("offline 과 free 값 업데이트하여 엔티티 필드에 비즈니스 로직 적용")
    void updateBizEvent(int basePrice, int maxPrice, boolean isFree) {

        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(isFree);


        // Given
        event = Event.builder()
                .location("낙성대 오렌지 연필")
                .build();
        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isTrue();

    }

    private static Object[] parameterProvider(){
        return new Object[] {
                new Object[] {0,0,true},
                new Object[] {100,0,false},
                new Object[] {0,100,false}
        };
    }

}