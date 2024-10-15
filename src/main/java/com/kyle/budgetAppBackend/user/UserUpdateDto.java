package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.budget.Budget;


import java.util.List;

public class UserUpdateDto {
    private Long id;
    private String username;
    private String email;
    private String password;



    private List<Budget> budgets;



    private List<Account> accounts;

    public List<Budget> getBudgets() {
        return budgets;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }



    public void setTransactions(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
