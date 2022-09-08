package com.example.demo.config.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

// PasswordEncoder 의 가장 단순한 구현 방법
public class PlainPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
