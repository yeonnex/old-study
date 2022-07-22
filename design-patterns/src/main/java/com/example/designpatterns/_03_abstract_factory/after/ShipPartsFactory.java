package com.example.designpatterns._03_abstract_factory.after;

// 추상 팩토리
public interface ShipPartsFactory {
    Anchor createAnchor();
    Wheel createWheel();
}
