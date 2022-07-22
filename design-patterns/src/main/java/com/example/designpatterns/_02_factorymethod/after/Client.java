package com.example.designpatterns._02_factorymethod.after;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.print(new WhiteShipFactory(), "whiteship", "keesun@gmail.com");
        client.print(new BlackShipFactory(), "blackship", "black@gmail.com");
    }

    private void print(ShipFactory shipFactory, String name, String email) {
        System.out.println(shipFactory.orderShip(name, email));
    }
}
