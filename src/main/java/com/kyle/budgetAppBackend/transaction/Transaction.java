package com.kyle.budgetAppBackend.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Transactions")
public class Transaction extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double amount;




    @ManyToOne()
    @JoinColumn(name="budget_id")

    private Budget budget;



    @ManyToOne()
    @JoinColumn(name="account_id")

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
