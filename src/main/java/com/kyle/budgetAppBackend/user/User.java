package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.base.BaseEntity;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.role.Role;
import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Budget> budgets;



    @JoinTable(name="users_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

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

    public String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
