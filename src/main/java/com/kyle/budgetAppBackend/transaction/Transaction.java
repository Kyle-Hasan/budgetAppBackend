package com.kyle.budgetAppBackend.transaction;

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
    @Column(nullable = false)
    private Date createdAt;



    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="budget_id")
    private Budget budget;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
