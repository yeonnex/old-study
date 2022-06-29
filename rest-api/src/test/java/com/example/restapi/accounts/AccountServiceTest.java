package com.example.restapi.accounts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Set;


@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("유저 이메일로 유저 찾기")
    void findUserByEmail() {
        // Given
        String email = "yeonnex@gmail.com";
        String password = "seoyeon";
        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.USER))
                .build();

        accountRepository.save(account);

        // When
        UserDetails userDetails = accountService.loadUserByUsername(email);
        // Then
        Assertions.assertEquals(password,userDetails.getPassword());
    }

    @Test
    @DisplayName("해당 유저네임이 DB 에 존재하지 않을 때 예외가 발생한다")
    void findUsernameNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername("noone@gmail.com");
        });
    }
}