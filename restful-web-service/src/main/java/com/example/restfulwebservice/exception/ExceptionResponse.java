package com.example.restfulwebservice.exception;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter // TODO getter 가 없으면 이 객체가 생성은 잘 되지만 최종적으로 응답 반환시 이 값이 내려가는게 아니라 스프링에 의해 덮어씌워진 값이 내려감
@Setter
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String description;
}
