package com.kyle.budgetAppBackend.account;

public class CurrentAccountDTO {
    private Long id;
    private String name;
    private Double currentAccountBalance;
    private Double amountDeposited;

    public CurrentAccountDTO(Long accountId, String accountName, Double currentAccountBalance, Double amountDeposited) {
        this.id = accountId;
        this.name = accountName;
        this.currentAccountBalance = currentAccountBalance;
        this.amountDeposited = amountDeposited;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    public void setCurrentAccountBalance(Double currentAccountBalance) {
        this.currentAccountBalance = currentAccountBalance;
    }

    public Double getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(Double amountDeposited) {
        this.amountDeposited = amountDeposited;
    }
}
