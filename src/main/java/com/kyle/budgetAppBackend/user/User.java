package com.kyle.budgetAppBackend.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.role.Role;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(BaseEntity.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL)

    private List<Budget> budgets = new ArrayList<Budget>();


    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<Account>();

    @ManyToMany
    @JoinTable(name="users_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<Role>();

    @Column(nullable = false, unique = true)
    public String email;


    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }


    public String getUsername() {
        return username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }


}
