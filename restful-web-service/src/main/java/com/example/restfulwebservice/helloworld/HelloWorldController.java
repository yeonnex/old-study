package com.example.restfulwebservice.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("hello world!");
    }

    @GetMapping("/hello-world/{name}")
    public HelloWorldBean helloWorldName(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello, %s", name));
    }


}
