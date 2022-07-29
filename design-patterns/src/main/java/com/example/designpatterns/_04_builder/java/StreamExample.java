package com.example.designpatterns._04_builder.java;

import java.util.stream.Stream;

public class StreamExample {
    public static void main(String[] args) {
        Stream.Builder<String> builder = Stream.builder();
        Stream<String> names = builder.add("seoyeon").add("pikachu").build();
    }
}
