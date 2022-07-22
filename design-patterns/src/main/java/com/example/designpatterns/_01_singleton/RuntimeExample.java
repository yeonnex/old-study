package com.example.designpatterns._01_singleton;

public class RuntimeExample {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.freeMemory());
    }
}
