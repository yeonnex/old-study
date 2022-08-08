package com.example.designpatterns._07_adapter.after.security;

public interface UserDetailsService {
    UserDetails loadUser(String username);
}
