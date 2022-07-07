package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter @Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password" , "ssn"})
public class User {
    private Long id;
    @Size(min=2, message = "Name 은 2글자 이상 입력해주세요.")
    private String name;
    @Past
    private Date joinDate;

    private String password;

    private String ssn;
}
