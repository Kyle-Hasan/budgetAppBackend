package com.kyle.budgetAppBackend.user;

public class SignupDto extends LoginDto{
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
