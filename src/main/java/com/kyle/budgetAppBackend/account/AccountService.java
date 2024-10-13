package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService extends BaseService<Account> {

    private TransactionRepository transactionRepository;
    public AccountService(BaseRepository<Account> baseRepository, TransactionRepository transactionRepository) {
        super(baseRepository);
    }

    @Override
    public Optional<Account> update(Account t) {
        Optional<Account> oldAccount = baseRepository.findById(t.getId());
        if (oldAccount.isPresent()) {
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
