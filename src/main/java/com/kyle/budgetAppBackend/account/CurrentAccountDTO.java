package com.kyle.budgetAppBackend.account;

public class CurrentAccountDTO {
    private Long id;
    private String name;
    private Double currentAccountBalance;
    private Double amountDeposited;



    private String icon;

    public CurrentAccountDTO(Long accountId, String accountName, Double currentAccountBalance, Double amountDeposited,String icon) {
        this.id = accountId;
        this.name = accountName;
        this.currentAccountBalance = Math.round(currentAccountBalance * 100.0) / 100.0;
        this.amountDeposited = Math.round(amountDeposited * 100.0) / 100.0;
        this.icon = icon;
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
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
