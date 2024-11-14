package com.kyle.budgetAppBackend.recurringTransaction;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import com.kyle.budgetAppBackend.user.User;
@RestController
@RequestMapping("/api/recurring")
public class RecurringController extends BaseController {
    private RecurringService recurringService;

    public RecurringController(UserService userService, RecurringService recurringService) {
        super(userService);
        this.recurringService = recurringService;
    }

    @DeleteMapping("/{id}")
    public void deleteRecurring(@PathVariable Long id) {
        recurringService.delete(id);
    }

    @PostMapping("")
    public RecurringTransaction createRecurring(@RequestBody RecurringTransaction r) {
        return recurringService.create(r);
    }

    @PatchMapping("")
    public RecurringTransaction updateRecurring(@RequestBody RecurringTransaction r) {
        return recurringService.update(r);
    }

    @GetMapping("/{id}")
    public RecurringTransaction getRecurring(@PathVariable Long id) {
        var optional = recurringService.get(id);
        return optional.orElse(null);
    }

    @GetMapping("")
    public List<RecurringTransaction> getAllForUser(){
        User user = getUser();
        if(user == null) {
            return null;
        }
        else {
            return recurringService.getRecurringTransactionsForUser(user.getId());
        }
    }
}
