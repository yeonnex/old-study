package com.example.designpatterns.factorymethod.before;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        ShipFactory.orderShip("whiteship", "keesun@gmail.com");
        ShipFactory.orderShip("blackship", "black@gmail.com");

    }
}
