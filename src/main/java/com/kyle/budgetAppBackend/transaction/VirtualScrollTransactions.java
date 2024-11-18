package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.VirtualScrollRequest;

public class VirtualScrollTransactions extends VirtualScrollRequest {
    public TransactionType getFilter() {
        return filter;
    }

    public void setFilter(TransactionType filter) {
        this.filter = filter;
    }

    private TransactionType filter;
}
