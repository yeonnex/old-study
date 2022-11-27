package com.shop.entity;

import com.shop.repository.ItemRepository;
import com.shop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@SpringBootTest
@WithMockUser(username = "tester", roles = "USER")
@Transactional
class OrdersTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("주문 -> 주문상품 영속성 전이 테스트")
    void cascadeTest() {
        Orders order = new Orders();
        for (int i = 0; i < 3; i++) {
            Item item = createItem(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setCount(i);
            orderItem.setOrderPrice(1000);
            orderItem.setItem(item);

            order.getOrderItems().add(orderItem);
        }
        orderRepository.save(order);
        em.flush();
        em.clear();

        Orders orders = orderRepository
                .findById(order.getId()).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(orders.getOrderItems().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    void orphanRemovalTest() {
        Item item = this.createItem(13);
        Orders order = new Orders();
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);

        order.getOrderItems().add(orderItem);
        order.getOrderItems().remove(0);

        orderRepository.save(order);

        em.flush();
        em.clear();

        Orders savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        Assertions.assertThat(savedOrder.getOrderItems().size()).isEqualTo(0);
    }

    private Item createItem(int i) {
        Item item = new Item();
        item.setItemNm("아이템" + i);
        item.setPrice(100 + i);
        item.setItemDetail("아이템" + i + "상세");
        itemRepository.save(item);
        return item;
    }
}