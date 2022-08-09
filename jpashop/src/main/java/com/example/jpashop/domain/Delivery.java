package com.example.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Order order;

    @Embedded
    private Address address;
    private DeliveryStatus status;

}
