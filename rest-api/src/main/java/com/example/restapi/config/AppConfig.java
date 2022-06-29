package com.example.restapi.config;

import com.example.restapi.accounts.Account;
import com.example.restapi.accounts.AccountRole;
import com.example.restapi.accounts.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    ApplicationRunner applicationRunner () {

        return new ApplicationRunner() {
            @Autowired
            AccountService accountService;

            @Autowired
            BCryptPasswordEncoder bCryptPasswordEncoder;
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account account = Account.builder()
                        .email("abcd@gmail.com")
                        .password("1234")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();
                account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                accountService.saveAccount(account);

            }
        };
    }
}
