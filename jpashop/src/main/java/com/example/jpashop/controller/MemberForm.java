package com.example.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberForm {

    @NotBlank(message = "회원 이름은 필수값입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
