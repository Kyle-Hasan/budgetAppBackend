package com.kyle.budgetAppBackend.budget;

public class BudgetGoalDTO {
    private Long id;
    private String name;
    private Double total;
    private Double currentSpent;

    public BudgetGoalDTO(Long budgetId, String budgetName, Double budgetAmount, Double totalSpent) {
        this.id = budgetId;
        this.name = budgetName;
        this.total = Math.round(budgetAmount * 100.0) / 100.0;
        this.currentSpent = totalSpent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getCurrentSpent() {
        return currentSpent;
    }

    public void setCurrentSpent(Double currentSpent) {
        this.currentSpent = currentSpent;
    }
}
