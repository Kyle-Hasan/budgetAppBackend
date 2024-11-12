package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.transaction.TransactionForListDTO;

import java.util.List;

public class AccountDTO {
    private  Long id;
    private String name;



    private double startingBalance;
    List<TransactionForListDTO> transactions;

    public AccountDTO(Long id, String name,  double startingBalance, List<TransactionForListDTO> transactions) {
        this.id = id;
        this.name = name;

        this.startingBalance = startingBalance;
        this.transactions = transactions;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
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



    public List<TransactionForListDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionForListDTO> transactions) {
        this.transactions = transactions;
    }
}