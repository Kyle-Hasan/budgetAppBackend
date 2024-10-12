package com.kyle.budgetAppBackend.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.transaction.Transaction;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")
public class Account extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private Double balance;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions = new ArrayList<Transaction>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
