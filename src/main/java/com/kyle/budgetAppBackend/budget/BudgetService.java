package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.transaction.*;
import com.kyle.budgetAppBackend.user.User;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.TransformService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService extends BaseService<Budget> {
    private TransactionRepository transactionRepository;
    private BudgetRepository budgetRepository;

    public BudgetService(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        super(budgetRepository);
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
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

    @Override
    public Budget create(Budget budget) {

        // check if any new transactions exist
        List<Transaction> uncreated = budget.getTransactions().stream().filter(t -> t.getId() == -1).toList();



        Budget retVal = super.create(budget);

        List<Transaction> created = transactionRepository.saveAll(uncreated.stream().map(t -> {
            t.setBudget(retVal);
            return t;
        }).toList());

        retVal.setTransactions(created);

        return retVal;
    }

    public List<ParentEntity> budgetSelectionsUser(Long userId) {
        var budgetObjs= budgetRepository.getBudgetsUser(userId);

        return budgetObjs.stream().map(o -> new ParentEntity((Long) o[0], (String) o[1])).toList();
    }

    public static List<TransactionForListDTO> convertTransactionsToDto(List<Transaction> transactions, long budgetId,String budgetName) {
        ParentEntity budget = new ParentEntity();
        budget.setName(budgetName);
        budget.setId(budgetId);
        return transactions.stream().map(TransactionService::convertTransactionToDto).toList();
    }

    public static BudgetDTO convertBudgetToDto(Budget budget) {
        List<TransactionForListDTO> transactionForListDTOS = convertTransactionsToDto(budget.getTransactions(), budget.getId(), budget.getName());
        return new BudgetDTO(budget.getId(), budget.getName(), budget.getDescription(), budget.getAmount(), transactionForListDTOS,budget.getIcon());
    }

    public static Budget convertDtoToBudget(BudgetDTO dto) {
            Budget budget = new Budget();
            List<Transaction> transactions = dto.transactions.stream().map(t-> {
                Transaction newT = new Transaction();
                newT.setId(t.getId());
                return newT;
            }).toList();

            return budget;
    }



}
