package com.example.designpatterns._03_abstract_factory.before;

import com.example.designpatterns._02_factorymethod.after.Ship;

public abstract class DefaultShipFactory {
    public Ship createShip() {
        return new Ship();
    }
}
