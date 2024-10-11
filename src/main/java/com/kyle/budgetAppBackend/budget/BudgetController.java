package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.budget.Budget;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private BudgetService budgetService;
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
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

    @PostMapping("")
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.create(budget);
    }

    @PatchMapping("")
    public Budget saveBudget(@RequestBody Budget budget) {
        var budgetOptional =  budgetService.update(budget);
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
}
