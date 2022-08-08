package com.example.designpatterns._07_adapter.after;

public class AccountService {
    public Account findAccountByUsername(String username) {
        return new Account();
    }

    public void createNewAccount(Account account) {

    }
    public void updateAccount(Account account) {

    }
}
