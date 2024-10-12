package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.account.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AccountController {
    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        var accountOptional =  accountService.get(id);
        if(accountOptional.isPresent()){
            return accountOptional.get();
        }
        else {
            return null;
        }
    }

    @PostMapping("")
    public Account createAccount(@RequestBody Account account) {
        return accountService.create(account);
    }

    @PutMapping("")
    public Account saveAccount(@RequestBody Account account) {
        var accountOptional =  accountService.update(account);
        if(accountOptional.isPresent()){
            return accountOptional.get();
        }
        else {
            return null;
        }
    }

    @DeleteMapping("")
    public void deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
    }

    @GetMapping("")
    public List<Account> getAccounts() {
        return accountService.getAll();
    }
}
