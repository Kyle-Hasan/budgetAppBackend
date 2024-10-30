package com.kyle.budgetAppBackend.transaction;

import java.time.LocalDateTime;
import java.util.Date;

public class TransactionForListDTO {
    private Long id;
    private double amount;
    private String name;
    private LocalDateTime date;

    private long budgetId;
    private long accountId;

    public TransactionForListDTO(Long id, double amount, String name, long budgetId, long accountId,LocalDateTime date) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.accountId = accountId;
        this.budgetId  = budgetId;
        this.date = date;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public long getAccountId() {
        return accountId;
    }
}
