package com.example.designpatterns._03_abstract_factory.after;

import com.example.designpatterns._02_factorymethod.after.Ship;
import com.example.designpatterns._02_factorymethod.after.WhiteShip;
import com.example.designpatterns._03_abstract_factory.before.DefaultShipFactory;

// 클라이언트
public class WhiteshipFactory extends DefaultShipFactory {
    private ShipPartsFactory shipPartsFactory;

    public WhiteshipFactory(ShipPartsFactory shipPartsFactory) {
        this.shipPartsFactory = shipPartsFactory;
    }

    @Override
    public Ship createShip() {
        Ship ship = new WhiteShip();
        ship.setAnchor(shipPartsFactory.createAnchor());
        ship.setWheel(shipPartsFactory.createWheel());

        return ship;
    }

    public static void main(String[] args) {

        // 기본 whiteship
        DefaultShipFactory whiteshipFactory = new WhiteshipFactory(new WhiteshipPartsFactory());
        Ship ship = whiteshipFactory.createShip();
        System.out.println(ship.getAnchor());
        // 프로 whiteship
        DefaultShipFactory whiteshipProFactory = new WhiteshipFactory(new WhiteshipProPartsFactory());
        Ship proShip = whiteshipProFactory.createShip();
        System.out.println(proShip.getWheel());
    }


}
