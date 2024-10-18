package com.kyle.budgetAppBackend.Token;


import com.kyle.budgetAppBackend.user.User;
import jakarta.persistence.*;

@Entity
public class Token {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String token;

    public boolean revoked;





    public boolean refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(boolean refreshToken) {
        this.refreshToken = refreshToken;
    }

}