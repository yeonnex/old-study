package com.example.designpatterns._05_prototype.java;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Student {
    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
