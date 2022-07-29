package com.example.designpatterns._04_builder.java;

public class StringBuilderExample {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        String result = stringBuilder.append("seoyeon").append("hello").toString();
    }
}
