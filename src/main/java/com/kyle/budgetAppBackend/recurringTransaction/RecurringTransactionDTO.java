package com.kyle.budgetAppBackend.recurringTransaction;

import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.transaction.TransactionType;

public class RecurringTransactionDTO {
    private Long id;
    private double amount;
    private String name;


    private FrequencyEnum frequency;

    private String icon;

    private TransactionType transactionType;

    private ParentEntity account;
    private ParentEntity budget;

    public RecurringTransactionDTO(Long id, double amount, String name, FrequencyEnum frequency, TransactionType type, ParentEntity account, ParentEntity budget,String icon) {
        this.id = id;
        this.amount = Math.round(amount * 100.0) / 100.0;
        this.name = name;
        this.frequency = frequency;
        this.transactionType = type;
        this.account = account;
        this.budget = budget;
        this.icon = icon;
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
    public FrequencyEnum getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyEnum frequency) {
        this.frequency = frequency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
