package com.kyle.budgetAppBackend.transaction;
import java.util.List;

public class TransactionPageResponse {
    List<TransactionForListDTO> transactions;
    Double totalSpent;
    Double totalDeposited;

    public TransactionPageResponse(List<TransactionForListDTO> transactions, Double totalSpent, Double totalDeposited) {
        this.transactions = transactions;
        this.totalSpent = totalSpent;
        this.totalDeposited = totalDeposited;
    }

    public List<TransactionForListDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionForListDTO> transactions) {
        this.transactions = transactions;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Double getTotalDeposited() {
        return totalDeposited;
    }

    public void setTotalDeposited(Double totalDeposited) {
        this.totalDeposited = totalDeposited;
    }
}
