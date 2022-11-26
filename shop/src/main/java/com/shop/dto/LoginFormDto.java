package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginFormDto {

    @NotNull(message = "이메일은 필수 입력값입니다")
    @Email
    private String email;


    @NotNull(message = "이메일은 필수 입력값입니다")
    @Length(min = 8, max = 16, message = "비밀번호는 8 ~ 16 글자 사이여야 합니다.")
    private String password;
}
