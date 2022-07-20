package com.example.designpatterns.singleton;


public class Settings {
    private static Settings instance;
    private Settings(){}

    // TODO 멀티 스레드에 안전하지 않다
    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }
}
