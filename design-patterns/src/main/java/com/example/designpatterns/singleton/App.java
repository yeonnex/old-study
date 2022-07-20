package com.example.designpatterns.singleton;

public class App {
    public static void main(String[] args) {
        Settings setting1 = Settings.getInstance();
        Settings setting2 = Settings.getInstance();
        System.out.println(setting1 == setting2);
    }
}
