package com.shop.dto;

import com.shop.constant.OrderStatus;
import com.shop.entity.Orders;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {

    public OrderHistDto(Orders order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getStatus();
    }
    private Long orderId; // 주문 아이디
    private String orderDate; // 주문날짜
    private OrderStatus orderStatus; // 주문 상태
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderItemDto orderItemDto) {
        orderItemDtoList.add(orderItemDto);
    }
}
