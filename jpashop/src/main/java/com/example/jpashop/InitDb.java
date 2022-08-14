package com.example.jpashop;

import com.example.jpashop.domain.*;
import com.example.jpashop.domain.item.Book;
import com.example.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 총 주문 2개
 * userA
 * - JPA1 BOOK
 * - JPA2 BOOK
 * userB
 * - Spring1 BOOK
 * - Spring2 BOOk
 */
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;


        public void dbInit() {
            Member userA = createMember("userA",
                    Address.builder().city("서울").street("밍밍거리").zipcode("0316").build());
            Member userB = createMember("userB",
                    Address.builder().city("부산").street("나뭇잎마을").zipcode("0613").build());

            Item jpa1 = createBook("JPA1", 1000, 100);
            Item jpa2 = createBook("JPA2", 2000, 20);

            Item spring1 = createBook("Spring1", 3000, 30);
            Item spring2 = createBook("Spring2", 3000, 30);

            OrderItem orderItemJPA1 = OrderItem.createOrderItem(jpa1, 10000, 1);
            OrderItem orderItemJPA2 = OrderItem.createOrderItem(jpa2, 20000, 1);

            OrderItem orderItemSpring1 = OrderItem.createOrderItem(spring1, 600, 1);
            OrderItem orderItemSpring2 = OrderItem.createOrderItem(spring2, 1200, 1);


            Delivery deliveryA = createDelivery(userA);
            Delivery deliveryB = createDelivery(userB);

            Order orderA = Order.createOrder(userA, deliveryA, orderItemJPA1, orderItemJPA2);
            Order orderB = Order.createOrder(userB, deliveryB, orderItemSpring1, orderItemSpring2);

            em.persist(orderA);
            em.persist(orderB);
        }

        private static Delivery createDelivery(Member user) {
            Delivery deliveryA = new Delivery();
            deliveryA.setAddress(user.getAddress());
            return deliveryA;
        }

        private Item createBook(String name, Integer price, Integer stockQuantity) {
            Item book = Book.builder().name("JPA1").price(1000).stockQuantity(100).build();
            em.persist(book);
            return book;
        }

        private Member createMember(String username, Address address) {
            Member user = Member.builder()
                    .name(username)
                    .address(address).build();
            em.persist(user);
            return user;
        }
    }
}