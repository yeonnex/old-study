package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final ModelMapper modelMapper = new ModelMapper();
    static List<User> users = new ArrayList<>();
    static Long userCount = 3L;

    static {
        users.add(User.builder().id(1L).name("pikachu").joinDate(new Date()).password("pass1").ssn("701010-1111111").build());
        users.add(User.builder().id(2L).name("chikorita").joinDate(new Date()).password("pass2").ssn("801010-1111111").build());
        users.add(User.builder().id(3L).name("ggobugi").joinDate(new Date()).password("pass3").ssn("901010-1111111").build());
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        user.setJoinDate(new Date());
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public MappingJacksonValue findUser(Long id) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "joinDate");

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("ID [%s] Not Found", id))));
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;

    }

    public Boolean deleteById(Long id) {
        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElseThrow(() -> new UserNotFoundException(String.format("ID [%s] Not Found. Remove Refused.", id)));
        return users.remove(user);
    }

    public ResponseEntity<User> updateUser(Long id, User user) {
        User savedUser = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("ID [%s] Not Found", id)));

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(user, savedUser);

        return ResponseEntity.ok(savedUser);
    }
}
