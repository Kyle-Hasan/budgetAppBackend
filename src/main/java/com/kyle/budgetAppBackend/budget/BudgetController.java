package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    private BudgetService budgetService;
    private UserService userService;
    public BudgetController(BudgetService budgetService,UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Budget getBudget(@PathVariable Long id) {
        var budgetOptional =  budgetService.get(id);
        if(budgetOptional.isPresent()){
            return budgetOptional.get();
        }
        else {
            return null;
        }
    }

    @PostMapping(value = "")
    public BudgetDTO createBudget(@RequestBody Budget budget ) {
        return BudgetService.convertBudgetToDto(budgetService.create(budget));
    }

    @PatchMapping("")
    public Budget saveBudget(@RequestBody Budget budget) {
        var budgetOptional =  budgetService.updateChangedOnly(budget);
        if(budgetOptional.isPresent()){
            return budgetOptional.get();
        }
        else {
            return null;
        }
    }

    @DeleteMapping("")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.delete(id);
    }

    @GetMapping("")
    public List<Budget> getBudgets() {
        return budgetService.getAll();
    }

    @GetMapping("/budgetSelections")
    public List<ParentEntity> getBudgetSelections() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = this.userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            return null;
        }
        else {
            User user = userOptional.get();
            return budgetService.budgetSelectionsUser(user.getId());
        }

    }
}
