package com.kyle.budgetAppBackend.account;

public class CurrentAccountDTO {
    private Long accountId;
    private String accountName;
    private Double currentAccountBalance;
    private Double amountDeposited;

    public CurrentAccountDTO(Long accountId, String accountName, Double currentAccountBalance, Double amountDeposited) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.currentAccountBalance = currentAccountBalance;
        this.amountDeposited = amountDeposited;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
