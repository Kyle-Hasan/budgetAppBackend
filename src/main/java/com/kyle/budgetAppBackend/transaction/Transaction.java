package com.kyle.budgetAppBackend.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.budget.Budget;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Transactions")
@EntityListeners(BaseEntity.class)
public class Transaction extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double amount;




    @ManyToOne()
    @JoinColumn(name="budget_id")
    @JsonBackReference(value = "userTransactions")
    private Budget budget;



    @ManyToOne()
    @JoinColumn(name="account_id")
    @JsonBackReference(value = "userTransactionsAccount")
    private Account account;

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Enumerated(EnumType.STRING)
    private TransactionType type;


    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
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



    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
