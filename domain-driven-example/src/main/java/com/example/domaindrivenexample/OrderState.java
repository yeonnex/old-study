package com.example.domaindrivenexample;

public enum OrderState {
    PAYMENT_WAITING,
    PREPARING,
    SHIPPED,
    DELIVERING,
    DELIVERY_COMPLETED,
    CANCELED;

    public boolean isShippingChangeable() {
        return switch (this) {
            case SHIPPED, PREPARING, PAYMENT_WAITING -> true;
            case DELIVERING, DELIVERY_COMPLETED, CANCELED -> false;
            //TODO
            // default 브랜치는 생성 금지
        };
    }


}
