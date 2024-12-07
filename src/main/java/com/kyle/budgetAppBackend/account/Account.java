package com.kyle.budgetAppBackend.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.recurringTransaction.RecurringTransaction;
import com.kyle.budgetAppBackend.transaction.Transaction;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")
@EntityListeners(BaseEntity.class)
public class Account extends BaseEntity {
    @Column(nullable = false)
    private String name;



    private Double startingBalance;



    private String icon;



    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    @JsonManagedReference(value = "userTransactionsAccount")
    private List<Transaction> transactions = new ArrayList<Transaction>();


    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "userRecurringTransactionsAccount")
    private List<RecurringTransaction> recurringTransactions = new ArrayList<RecurringTransaction>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(Double startingBalance) {
        this.startingBalance = startingBalance;
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
