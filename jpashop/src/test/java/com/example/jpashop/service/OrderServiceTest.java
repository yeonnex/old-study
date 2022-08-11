package com.example.jpashop.service;

import com.example.jpashop.domain.*;
import com.example.jpashop.domain.item.Book;
import com.example.jpashop.domain.item.Item;
import com.example.jpashop.exception.NotEnoughStockException;
import com.example.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("상품 주문 - 성공")
    public void 상품주문() {
        int quantity = 100;
        int count = 20;
        int price = 120000;

        Member member = createMember("seoyeon");

        Item book = createBook("JPA 심화", price, quantity);
        entityManager.persist(book);

        Long orderId = orderService.order(member.getId(), book.getId(), count);
        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(count * price, order.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(quantity - count, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다");
    }

    @Test
    @DisplayName("상품 주문 - 실패 - 수량부족")
    void 상품주문_재고수량초과() {
        Member member = Member.builder().name("seoyeon").build();
        Item item = Item.builder().price(1000).stockQuantity(20).build(); // 재고는 20개
        Delivery delivery = Delivery.builder().address(member.getAddress()).build();

        assertThrows(NotEnoughStockException.class,
                () -> OrderItem.createOrderItem(item, item.getPrice(), 100)); // 재고는 20개인데 100개 주문
    }

    @Test
    @DisplayName("주문 취소 - 성공")
    void 주문취소() {
        Member member = createMember("seoyeon");

        Item item = createBook("스프링 부트", 1000, 100);

        Long orderId = orderService.order(member.getId(), item.getId(), 10);

        orderService.cancelOrder(orderId);
        Order savedOrder = orderRepository.findOne(orderId);

        assertEquals(savedOrder.getStatus(), OrderStatus.CANCELED, "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(100, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 늘어나야 한다.");
    }

    @Test
    @DisplayName("주문 취소 - 실패")
    void cancelOrderFail() {

    }


    private Member createMember(String name) {
        Member member = Member.builder().name(name).build();
        entityManager.persist(member);
        return member;
    }

    private Item createBook(String name, int price, int quantity) {
        Item book = Book.builder().price(price).name(name).stockQuantity(quantity).build();
        entityManager.persist(book);
        return book;
    }

}