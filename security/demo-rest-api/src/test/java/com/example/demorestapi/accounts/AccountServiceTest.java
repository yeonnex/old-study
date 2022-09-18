package com.example.demorestapi.accounts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void findByUsername() {
        // Given
        String username = "seoyeon@gmail.com";
        String password = "ming123";
        Account account = Account.builder().email(username)
                .password(password)
                .roleType(RoleType.ROLE_USER).build();

        accountRepository.save(account);

        // When
        UserDetails userDetails = accountService.loadUserByUsername(account.getEmail());
        assertThat(userDetails.getUsername()).isEqualTo(username);

    }

}