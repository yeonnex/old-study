package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    static List<User>  users = new ArrayList<>();
    static Long userCount = 3L;
    static {
        users.add(User.builder().id(1L).name("pikachu").joinDate(new Date()).build());
        users.add(User.builder().id(2L).name("chikorita").joinDate(new Date()).build());
        users.add(User.builder().id(3L).name("ggobugi").joinDate(new Date()).build());
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
    public User findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new UserNotFoundException(String.format("ID [%s] Not Found", id)));
    }

    public Boolean deleteById(Long id) {
        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElseThrow(()-> new UserNotFoundException(String.format("ID [%s] Not Found. Remove Refused.", id)));
        return users.remove(user);
    }
}
