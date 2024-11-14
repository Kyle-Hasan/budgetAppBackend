package com.kyle.budgetAppBackend.recurringTransaction;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.transaction.Transaction;
import com.kyle.budgetAppBackend.transaction.TransactionType;
import com.kyle.budgetAppBackend.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="recurringTransactions")
@EntityListeners(BaseEntity.class)
public class RecurringTransaction extends BaseEntity {
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
    @Column(nullable = false)
    private FrequencyEnum frequency;



    @Column(nullable = false)
    private TransactionType transactionType;

    public RecurringTransaction(FrequencyEnum frequency, Account account, Budget budget, double amount, String name,TransactionType transactionType) {
        this.frequency = frequency;
        this.account = account;
        this.budget = budget;
        this.amount = amount;
        this.name = name;
        this.transactionType = transactionType;
    }

    public RecurringTransaction() {

    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public FrequencyEnum getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyEnum frequency) {
        this.frequency = frequency;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
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
}
