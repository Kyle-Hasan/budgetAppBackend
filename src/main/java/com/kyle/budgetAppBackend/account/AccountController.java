package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.account.AccountService;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;
    private UserService userService;
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
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
        var accountOptional =  accountService.updateChangedOnly(account);
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

    @GetMapping("/accountSelections")
    public List<ParentEntity> getAccountSelections() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = this.userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            return null;
        }
        else {
            User user = userOptional.get();
            return accountService.getAccountSelections(user.getId());
        }

    }
}
