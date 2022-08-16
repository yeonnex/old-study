package com.example.jpashop.api;

import com.example.jpashop.api.dto.DeliveryDto;
import com.example.jpashop.api.dto.ItemDto;
import com.example.jpashop.api.dto.MemberDto;
import com.example.jpashop.api.dto.OrderItemDto;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.repository.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
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


    @Getter @Setter
    static class OrderDto {
        public OrderDto(Order order) {
            this.memberDto = modelMapper.map(order.getMember(), MemberDto.class);
            order.getOrderItems().forEach(o -> {
                this.orderItemDtos.add(modelMapper.map(o, OrderItemDto.class));
                this.itemDto = modelMapper.map(o.getItem(), ItemDto.class);
            });
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.deliveryDto = modelMapper.map(order.getDelivery(), DeliveryDto.class);
        }

        private MemberDto memberDto;
        private List<OrderItemDto> orderItemDtos = new ArrayList<>();
        private ItemDto itemDto;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private DeliveryDto deliveryDto;
    }
}
