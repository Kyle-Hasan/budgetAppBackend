package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.account.AccountService;
import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import com.kyle.budgetAppBackend.transaction.TransactionService;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/accounts")
public class AccountController extends BaseController {
    private AccountService accountService;

    public AccountController(AccountService accountService, UserService userService) {
        super(userService);
        this.accountService = accountService;

    }

    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {

        var user = getUser();

        if(user == null) {
            return null;
        }

        Optional<Account> optional = accountService.get(id);
        if(optional.isPresent()) {
            return AccountService.convertToDto(optional.get());
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

    @DeleteMapping("/{id}")
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

    @GetMapping("/userAccounts")
    public AccountPageDTO getUserAccounts(@RequestParam String startDate, @RequestParam String endDate) {
        User user = getUser();
        if(user == null) {
            return  null;
        }
        return accountService.getAccountsPageDTO(user.getId(),startDate,endDate);

    }




}
