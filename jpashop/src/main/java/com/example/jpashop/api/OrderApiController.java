package com.example.jpashop.api;

import com.example.jpashop.domain.Delivery;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderItem;
import com.example.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll();
        all.stream().forEach(o -> {
            o.getMember().getName(); // LAZY 불러오기
            o.getDelivery().getStatus(); // LAZY 불러오기
            o.getOrderItems().get(0).getItem().getStockQuantity(); // LAZY 불러오기
        });
        return all;
    }
}
