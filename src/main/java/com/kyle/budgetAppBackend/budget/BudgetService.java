package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService extends BaseService<Budget> {
    private TransactionRepository transactionRepository;

    public BudgetService(BaseRepository<Budget> baseRepository, TransactionRepository transactionRepository) {
        super(baseRepository);
    }
    @Override
    public Optional<Budget> update(Budget t) {
        Optional<Budget> oldBudget = baseRepository.findById(t.getId());
        if (oldBudget.isPresent()) {
            var transactionsIds = t.getTransactions().stream()
                    .map(transaction -> {
                        return transaction.getId();
                    })
                    .collect(Collectors.toList());

            var transactions = transactionRepository.findAllById(transactionsIds);
            t.setTransactions(transactions);
            return Optional.of(baseRepository.save(t));
        }
        return Optional.empty();
    }

}
