package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 사용자 관리를 담당하는 UserDetailsService 와 PasswordEncoder 의 두 빈만 포함.
 */
@Configuration
public class UserManagementConfig {
//    @Bean
//    public UserDetailsService userDetailsService(){
//        var userDetailsService = new InMemoryUserDetailsManager();
//
//        var user = User.withUsername("john")
//                .password("12345")
//                .authorities("read").build();
//        userDetailsService.createUser(user);
//        return userDetailsService;
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
}
