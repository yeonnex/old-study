package com.example.domaindrivenexample;

public class OrderLine {
    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        this.price = new Money(price.getValue());
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    public int getAmounts() {
        return amounts.getValue();
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }
}
