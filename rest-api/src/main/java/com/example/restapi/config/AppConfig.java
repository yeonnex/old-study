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
                // admin  저장
                Account admin = Account.builder()
                        .email("admin@gmail.com")
                        .password("admin")
                        .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                        .build();
                admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
                accountService.saveAccount(admin);

                // user 저장
                Account user = Account.builder()
                        .email("user@gmail.com")
                        .password("user")
                        .roles(Set.of(AccountRole.USER))
                        .build();
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                accountService.saveAccount(user);

            }
        };
    }
}
