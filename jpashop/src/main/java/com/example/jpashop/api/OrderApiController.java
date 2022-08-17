package com.example.jpashop.api;

import com.example.jpashop.api.dto.DeliveryDto;
import com.example.jpashop.api.dto.ItemDto;
import com.example.jpashop.api.dto.MemberDto;
import com.example.jpashop.api.dto.OrderItemDto;
import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderItem;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.repository.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * V1 엔티티를 직접 노출
     *
     * @return
     */
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

    /**
     * V2 엔티티를 DTO 로 변환
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> all = orderRepository.findAll();
        return all.stream().map(OrderDto::new)
                .collect(Collectors.toList());
    }

    /**
     * V3 엔티티를 DTO 로 변환 - 페치조인 최적화
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> all = orderRepository.findAllWithItem();

        for (Order order : all) {
            System.out.println(order);
            System.out.println(order.getId());
        }
        return all.stream().map(OrderDto :: new)
                .collect(Collectors.toList());
    }

    /**
     * V3.1 엔티티를 DTO 로 변환 - 페이징
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
    }



    @Getter @Setter
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            order.getOrderItems().stream().forEach(o -> o.getItem().getName());
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }
        @Getter @Setter
        static class OrderItemDto {
            private String itemName;
            private int orderPrice;
            private int count;

            public OrderItemDto(OrderItem orderItem) {
                this.itemName = orderItem.getItem().getName();
                this.orderPrice = orderItem.getItem().getPrice();
                this.count = orderItem.getCount();
            }
        }
    }
}
