package com.example.designpatterns._03_abstract_factory.after;

import com.example.designpatterns._03_abstract_factory.before.WhiteAnchor;
import com.example.designpatterns._03_abstract_factory.before.WhiteWheel;

public class WhiteshipPartsFactory implements ShipPartsFactory{

    @Override
    public Anchor createAnchor() {
        return new WhiteAnchor();
    }

    @Override
    public Wheel createWheel() {
        return new WhiteWheel();
    }
}
