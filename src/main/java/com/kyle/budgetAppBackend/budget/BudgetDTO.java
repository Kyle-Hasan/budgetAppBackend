package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.transaction.TransactionForListDTO;

import java.util.List;

public class BudgetDTO {
    private  Long id;
    private String name;

    private double amount;
    List<TransactionForListDTO> transactions;
    private String icon;

    public BudgetDTO(Long id, String name,  double amount, List<TransactionForListDTO> transactions,String icon) {
        this.id = id;
        this.name = name;

        this.amount = Math.round(amount * 100.0) / 100.0;
        this.transactions = transactions;
        this.icon = icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
