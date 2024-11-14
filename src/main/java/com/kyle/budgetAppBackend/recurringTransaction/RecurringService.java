package com.kyle.budgetAppBackend.recurringTransaction;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
public class RecurringService extends BaseService<RecurringTransaction> {
    private RecurringRepository recurringRepository;
    public RecurringService(RecurringRepository recurringRepository) {
        super(recurringRepository);
        this.recurringRepository = recurringRepository;
    }

    public RecurringTransaction update(RecurringTransaction r) {
        Optional<RecurringTransaction> oldR = baseRepository.findById(r.getId());
        return oldR.map(recurringTransaction -> updateChangedOnly(r, recurringTransaction)).orElse(null);
    }

    public List<RecurringTransaction> getRecurringTransactionsForUser(Long userId) {
        return recurringRepository.getUserTransactions(userId);
    }
}
