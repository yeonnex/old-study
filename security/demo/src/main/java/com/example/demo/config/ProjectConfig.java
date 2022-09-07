package com.example.demo.config;

import com.example.demo.SecurityUser;
import com.example.demo.domain.User;
import com.example.demo.service.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        User user = User.builder().username("john").password("12345").authority("READ").build();
        UserDetails u = new SecurityUser(user);
        List<UserDetails> users = List.of(u);
        return new InMemoryUserDetailsService(users);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
