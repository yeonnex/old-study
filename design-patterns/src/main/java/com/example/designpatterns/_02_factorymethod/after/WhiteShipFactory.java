package com.example.designpatterns._02_factorymethod.after;



public class WhiteShipFactory implements ShipFactory {

    @Override
    public Ship createShip() {
        return new WhiteShip();
    }
}
