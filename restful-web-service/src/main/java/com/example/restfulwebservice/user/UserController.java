package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> retreiveAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable Long id){
        return userService.findUser(id);
    }

    @PostMapping("/users")
    ResponseEntity<URI> createUser(@RequestBody User user) {
        User saved = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
