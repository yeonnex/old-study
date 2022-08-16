package com.example.jpashop.api.dto;

import com.example.jpashop.domain.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryDto {
    private DeliveryStatus deliveryStatus;
}
