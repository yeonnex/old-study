package com.example.designpatterns._04_builder.java;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SpringExample {
    public static void main(String[] args) {
        UriComponents javaStudy = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.whiteship.com")
                .path("java playlist")
                .build();
        System.out.println(javaStudy);
    }
}
