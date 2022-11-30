package com.example.domaindrivenexample;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
    @Test
    @DisplayName("두 엔티티 객체의 식별자가 같다면 두 엔티티는 같다")
    void entityId() {
        Order order_1 = new Order();
        order_1.setOrderNumber("100");

        Order order_2 = new Order();
        order_2.setOrderNumber("100");
        Assertions.assertThat(order_1).isEqualTo(order_2);
    }
}