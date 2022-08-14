package com.example.jpashop.api;

import com.example.jpashop.domain.Order;
import com.example.jpashop.repository.OrderRepository;
import com.example.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne (ManyToOne, OneToOne 성능 최적화) <br/>
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orders() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName(); // LAZY 강제 초기화
        }
        return all;
    }
}
