package com.kyle.budgetAppBackend.user;

public class SignupDto extends LoginDto{
    private String email;

    public SignupDto(String username, String password,String email) {
        super(email,password);
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
