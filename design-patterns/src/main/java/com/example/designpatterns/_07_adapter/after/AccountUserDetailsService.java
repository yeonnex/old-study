package com.example.designpatterns._07_adapter.after;

import com.example.designpatterns._07_adapter.after.security.UserDetails;
import com.example.designpatterns._07_adapter.after.security.UserDetailsService;

public class AccountUserDetailsService implements UserDetailsService {
    AccountService accountService;

    public AccountUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUser(String username) {
        Account account = accountService.findAccountByUsername(username);
        return new AccountUserDetails(account);
    }
}
