package com.example.restfulwebservice.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@Builder
public class User {
    private Long id;
    private String name;
    private Date joinDate;
}
