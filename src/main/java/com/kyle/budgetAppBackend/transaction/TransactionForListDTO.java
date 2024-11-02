package com.kyle.budgetAppBackend.transaction;

import java.time.LocalDateTime;
import java.util.Date;

public class TransactionForListDTO {
    private Long id;
    private double amount;
    private String name;
    private LocalDateTime date;

    private ParentEntity account;
    private ParentEntity budget;



    public TransactionForListDTO(Long id, double amount, String name, LocalDateTime date, ParentEntity account, ParentEntity budget) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.date = date;
        this.account = account;
        this.budget = budget;
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


    public ParentEntity getAccount() {
        return account;
    }

    public void setAccount(ParentEntity account) {
        this.account = account;
    }

    public ParentEntity getBudget() {
        return budget;
    }

    public void setBudget(ParentEntity budget) {
        this.budget = budget;
    }
}
