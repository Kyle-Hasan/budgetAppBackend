package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class TransactionService extends BaseService<Transaction> {

    private TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
    }

    public static TransactionForListDTO convertTransactionToDto(Transaction t) {
        ParentEntity account = null;
        ParentEntity budget = null;
        var fullAccount = t.getAccount();
        var fullBudget = t.getBudget();
        if(fullBudget != null) {
            budget = new ParentEntity(fullBudget.getId(),fullBudget.getName());
        }

        if(fullAccount != null) {
            account = new ParentEntity(fullAccount.getId(),fullAccount.getName());
        }
        String type = null;
        if(t.getType() != null) {
            type = t.getType().toString();
        }
        return new TransactionForListDTO(t.getId(),t.getAmount(),t.getName(),t.getDate(),account,budget,type);

    }

    public List<TransactionForListDTO> getUserTransactions(Long userId, String startDate, String endDate) {
        List<Transaction> transactions = transactionRepository.getUserTransactions(userId,startDate,endDate);
        return transactions.stream().map(TransactionService::convertTransactionToDto).toList();
    }

    public TransactionPageResponse getTransactionPage(Long userId,String startDate, String endDate) {
        List<TransactionForListDTO> transactionForListDTOS = getUserTransactions(userId,startDate,endDate);
        Double totalSpent = transactionForListDTOS.stream()
                .filter(x->  x.getType() != null && x.getType().
                        equals(TransactionType.EXPENSE.toString())).mapToDouble(TransactionForListDTO::getAmount).sum();
        Double totalDeposited = transactionForListDTOS.stream().filter(x-> x.getType() != null && x.getType().equals(TransactionType.INCOME.toString())).mapToDouble(TransactionForListDTO::getAmount).sum();

        return new TransactionPageResponse(transactionForListDTOS,totalSpent,totalDeposited);

    }
}
