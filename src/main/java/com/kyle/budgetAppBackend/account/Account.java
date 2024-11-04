package com.kyle.budgetAppBackend.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.transaction.Transaction;
import com.kyle.budgetAppBackend.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")
@EntityListeners(BaseEntity.class)
public class Account extends BaseEntity {
    @Column(nullable = false)
    private String name;


    @Column(updatable = false)
    private Double balance;





    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    @JsonManagedReference(value = "userTransactionsAccount")
    private List<Transaction> transactions = new ArrayList<Transaction>();

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


}
