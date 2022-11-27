package com.shop.entity;

import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class OrderItemTest {
    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("지연 로딩 테스트")
    @WithMockUser(username = "seoyeon", roles = "USER")
    void lazyLoadingTest() {
        Orders order = createOrder();
        em.flush();
        em.clear();
        Long orderItemId = order.getOrderItems().get(0).getId();

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        System.out.println(orderItem.getOrder().getClass()); // 지연로딩으로 설정했기에 프록시타입
    }

    private Orders createOrder() {
        Orders order = new Orders();
        Item item = new Item();
        item.setItemNm("물풍선");
        item.setPrice(1000);
        item.setItemDetail("상품 상세");
        item.setStockNumber(100);

        itemRepository.save(item);
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrder(order);

        order.getOrderItems().add(orderItem);
        Member member = new Member();
        member.setName("장서연");
        memberRepository.save(member);
        order.setMember(member);
        orderRepository.save(order);

        return order;
    }
}