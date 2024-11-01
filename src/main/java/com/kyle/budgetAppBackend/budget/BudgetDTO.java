package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.transaction.TransactionForListDTO;

import java.util.List;

public class BudgetDTO {
    private  Long id;
    private String name;
    private String description;
    private double amount;
    List<TransactionForListDTO> transactions;

    public BudgetDTO(Long id, String name, String description, double amount, List<TransactionForListDTO> transactions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.transactions = transactions;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<TransactionForListDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionForListDTO> transactions) {
        this.transactions = transactions;
    }
}
