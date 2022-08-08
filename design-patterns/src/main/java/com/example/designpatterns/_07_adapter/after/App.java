package com.example.designpatterns._07_adapter.after;

import com.example.designpatterns._07_adapter.after.security.LoginHandler;
import com.example.designpatterns._07_adapter.after.security.UserDetailsService;

public class App {
    public static void main(String[] args) throws IllegalAccessException {
        AccountService accountService = new AccountService();
        UserDetailsService userDetailsService = new AccountUserDetailsService(accountService);
        LoginHandler loginHandler = new LoginHandler(userDetailsService);

        String login = loginHandler.login("keesun", "keesun");
        System.out.println(login);

    }
}
