package com.example.designpatterns._02_factorymethod.after;

import com.example.designpatterns._03_abstract_factory.after.Anchor;
import com.example.designpatterns._03_abstract_factory.after.Wheel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ship {
    private String name;
    private String logo;
    private String color;
    private Anchor anchor;
    private Wheel wheel;

}
