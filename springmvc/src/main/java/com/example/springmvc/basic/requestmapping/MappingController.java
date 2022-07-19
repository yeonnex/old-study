package com.example.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping")
public class MappingController {


    @PostMapping("/users")
    public String createUser() {
        return "create";
    }
    @GetMapping("/users/{userId}")
    public String findUser(@PathVariable String userId) {
        return userId;
    }
    @PatchMapping("/users/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update user";
    }
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete user";
    }
}
