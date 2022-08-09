package com.example.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private Integer price;
    private Integer stockQuantity;

    @ManyToMany
    private List<Category> categories;
}
