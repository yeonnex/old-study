package com.example.designpatterns._07_adapter.java;

import lombok.Getter;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // 이런 애노테이션 방식의 핸들러 말고도, 스프링은 다양한 형식의 핸들러 방식을 제공한. 핸들러의 형식은 매우 다양하다
    @GetMapping("/hello")
    public String hello() {
        return "hi";
    }
}
