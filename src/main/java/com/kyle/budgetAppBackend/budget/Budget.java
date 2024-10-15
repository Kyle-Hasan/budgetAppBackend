package com.kyle.budgetAppBackend.budget;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.transaction.Transaction;
import com.kyle.budgetAppBackend.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="budgets")
@EntityListeners(BaseEntity.class)
public class Budget extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private double amount;






    @OneToMany(mappedBy = "budget",cascade = CascadeType.ALL)

    private List<Transaction> transactions = new ArrayList<Transaction>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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



}
