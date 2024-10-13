package com.kyle.budgetAppBackend.budget;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.SqlResultSetMapping;

public class BudgetGoalDTO {
    private Long budgetId;
    private String budgetName;
    private Double budgetAmount;
    private Double totalSpent;

    public BudgetGoalDTO(Long budgetId, String budgetName, Double budgetAmount, Double totalSpent) {
        this.budgetId = budgetId;
        this.budgetName = budgetName;
        this.budgetAmount = budgetAmount;
        this.totalSpent = totalSpent;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }
}
