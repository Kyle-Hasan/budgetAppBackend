package com.kyle.budgetAppBackend.budget;

public class BudgetGoalDTO {
    private Long id;
    private String name;
    private Double amount;
    private Double total;

    public BudgetGoalDTO(Long budgetId, String budgetName, Double budgetAmount, Double totalSpent) {
        this.id = budgetId;
        this.name = budgetName;
        this.amount = budgetAmount;
        this.total = totalSpent;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
