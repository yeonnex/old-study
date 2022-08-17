package com.example.jpashop.repository.order.query;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemQueryDto {
    private String itemName;
    private int orderPrice;
    private int count;
}
