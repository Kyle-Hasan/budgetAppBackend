package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.transaction.Transaction;
import com.kyle.budgetAppBackend.transaction.TransactionForListDTO;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService extends BaseService<Budget> {
    private TransactionRepository transactionRepository;

    public BudgetService(BaseRepository<Budget> baseRepository, TransactionRepository transactionRepository) {
        super(baseRepository);
        this.transactionRepository = transactionRepository;
    }

    public Optional<Budget> updateChangedOnly(Budget t) {
        Optional<Budget> oldBudget = baseRepository.findById(t.getId());
        if (oldBudget.isPresent()) {
            var transactionsIds = t.getTransactions().stream()
                    .map(transaction -> {
                        return transaction.getId();
                    })
                    .collect(Collectors.toList());

            var transactions = transactionRepository.findAllById(transactionsIds);
            t.setTransactions(transactions);
            return Optional.ofNullable(super.updateChangedOnly(t, oldBudget.get()));
        }
        return Optional.empty();
    }

    private static List<TransactionForListDTO> convertTransactionsToDto(List<Transaction> transactions, long budgetId) {
        return transactions.stream().map(t -> new TransactionForListDTO(t.getId(),t.getAmount(),t.getName(),budgetId,t.getAccount().getId(),t.getDate())).toList();
    }

    public static BudgetDTO convertBudgetToDto(Budget budget) {
        List<TransactionForListDTO> transactionForListDTOS = convertTransactionsToDto(budget.getTransactions(), budget.getId());
        return new BudgetDTO(budget.getId(), budget.getName(), budget.getDescription(), budget.getAmount(), transactionForListDTOS);
    }

}
