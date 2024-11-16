package com.kyle.budgetAppBackend.transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TransactionForListDTO {
    private Long id;
    private double amount;
    private String name;
    private LocalDate date;



    private String type;

    private ParentEntity account;
    private ParentEntity budget;



    public TransactionForListDTO(Long id, double amount, String name, LocalDate date, ParentEntity account, ParentEntity budget, String type) {
        this.id = id;
        this.amount = Math.round(amount * 100.0) / 100.0;
        this.name = name;
        this.date = date;
        this.account = account;
        this.budget = budget;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
