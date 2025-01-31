package com.kyle.budgetAppBackend.budget;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.recurringTransaction.RecurringTransaction;
import com.kyle.budgetAppBackend.transaction.Transaction;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="budgets")
@EntityListeners(BaseEntity.class)
public class Budget extends BaseEntity {
    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private double amount;

    @OneToMany(mappedBy = "budget",cascade = CascadeType.ALL)
    @JsonManagedReference(value = "userTransactions")
    private List<Transaction> transactions = new ArrayList<Transaction>();


    @JsonIgnore
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "userRecurringTransactions")
    private List<RecurringTransaction> recurringTransactions = new ArrayList<RecurringTransaction>();



    private String icon;

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




    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);

        transaction.setBudget(this);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<RecurringTransaction> getRecurringTransactions() {
        return recurringTransactions;
    }

    public void setRecurringTransactions(List<RecurringTransaction> recurringTransactions) {
        this.recurringTransactions = recurringTransactions;
    }



}
