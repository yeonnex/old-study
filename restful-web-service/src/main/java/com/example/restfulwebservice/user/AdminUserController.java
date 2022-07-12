package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        List<User> users = userService.findAll();

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("ID [%s] Not Found", id))));
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);


        mappingJacksonValue.setFilters(filters);
        return users;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue findUser(@PathVariable Long id){
        return userService.findUser(id);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }


}
