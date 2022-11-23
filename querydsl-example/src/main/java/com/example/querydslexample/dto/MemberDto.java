package com.example.querydslexample.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
    private String username;
    private int age;


    @QueryProjection // compileQueryDsl 하면 이 DTO 로 Q class 가 생성되게 할 수 있다. 단점은 DTO 가 queryDsl 에 의존하게 됨..
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
