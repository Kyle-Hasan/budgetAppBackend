package com.kyle.budgetAppBackend.account;

import java.util.List;

public class AccountPageDTO {
    List<CurrentAccountDTO> accounts;
    Double totalDeposited;



    Double totalBalance;

    public AccountPageDTO(List<CurrentAccountDTO> accounts,Double totalDeposited, Double totalBalance) {
        this.totalDeposited = Math.round(totalDeposited * 100.0) / 100.0;
        this.accounts = accounts;
        this.totalBalance = Math.round(totalBalance * 100.0) / 100.0;
    }

    public List<CurrentAccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<CurrentAccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Double getTotalDeposited() {
        return totalDeposited;
    }

    public void setTotalDeposited(Double totalDeposited) {
        this.totalDeposited = totalDeposited;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
