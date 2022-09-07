package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserBuilderTest {
    @Test
    void userBuilder(){
        UserDetails user = User.builder()
                .username("seoyeon")
                .password("1234")
                .authorities("read", "write")
                .build();
    }
}
