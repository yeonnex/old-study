package com.example.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Order order;

    private Integer orderPrice;
    private Integer count;


}
