package com.example.designpatterns._07_adapter.before.security;

public interface UserDetailsService {
    UserDetails loadUser(String username);
}
