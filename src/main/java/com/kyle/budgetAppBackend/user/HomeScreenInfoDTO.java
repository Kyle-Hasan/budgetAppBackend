package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.account.CurrentAccountDTO;
import com.kyle.budgetAppBackend.budget.BudgetGoalDTO;

import java.util.List;

public class HomeScreenInfoDTO {
    List<CurrentAccountDTO> currentAccounts;
    List<BudgetGoalDTO> budgetGoals;
    Double totalSpent;
    Double totalDeposited;

    public HomeScreenInfoDTO(List<CurrentAccountDTO> currentAccounts, List<BudgetGoalDTO> budgetGoals, Double totalSpent, Double totalDeposited) {
        this.currentAccounts = currentAccounts;
        this.budgetGoals = budgetGoals;
        this.totalSpent = totalSpent;
        this.totalDeposited = totalDeposited;
    }

    public List<CurrentAccountDTO> getCurrentAccounts() {
        return currentAccounts;
    }

    public void setCurrentAccounts(List<CurrentAccountDTO> currentAccounts) {
        this.currentAccounts = currentAccounts;
    }

    public List<BudgetGoalDTO> getBudgetGoals() {
        return budgetGoals;
    }

    public void setBudgetGoals(List<BudgetGoalDTO> budgetGoals) {
        this.budgetGoals = budgetGoals;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Double getTotalDeposited() {
        return totalDeposited;
    }

    public void setTotalDeposited(Double totalDeposited) {
        this.totalDeposited = totalDeposited;
    }
}
