package com.kyle.budgetAppBackend.recurringTransaction;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.account.CurrentAccountDTO;
import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.transaction.Transaction;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class RecurringService extends BaseService<RecurringTransaction> {
    private RecurringRepository recurringRepository;
    private TransactionRepository transactionRepository;
    public RecurringService(RecurringRepository recurringRepository,TransactionRepository transactionRepository) {
        super(recurringRepository);
        this.recurringRepository = recurringRepository;
        this.transactionRepository = transactionRepository;
    }

    public RecurringTransaction update(RecurringTransaction r) {
        Optional<RecurringTransaction> oldR = baseRepository.findById(r.getId());
        return oldR.map(recurringTransaction -> updateChangedOnly(r, recurringTransaction)).orElse(null);
    }

    public List<RecurringTransaction> getRecurringTransactionsForUser(Long userId) {
        return recurringRepository.getUserTransactions(userId);
    }

    public static RecurringTransactionDTO convertToDTO(RecurringTransaction r) {
        ParentEntity account = null;
        ParentEntity budget = null;
        Account fullAccount = r.getAccount();
        Budget fullBudget = r.getBudget();
        if(fullBudget !=null) {
            budget = new ParentEntity(fullBudget.getId(),fullBudget.getName());
        }
        if(fullAccount != null) {
            account = new ParentEntity(fullAccount.getId(),fullAccount.getName());
        }
        return new RecurringTransactionDTO(r.getId(),r.getAmount(),r.getName(),r.getFrequency(),r.getTransactionType(),account,budget,r.getIcon());

    }

    @Scheduled(cron = "0 0 0 * * *")
    public void processRecurring() {
         LocalDate today = LocalDate.now();
         LocalDate lastMonth = today.minusMonths(1);
         LocalDate lastWeek = today.minusWeeks(1);
         LocalDate twoWeeksAgo = today.minusWeeks(2);

        //id,name,amount,transactionType,user_id
        List<RecurringTransaction> recurring = recurringRepository.getRecurringForUpdate(lastWeek,twoWeeksAgo,
                lastMonth,FrequencyEnum.Daily,FrequencyEnum.Weekly,FrequencyEnum.Biweekly,FrequencyEnum.Monthly);
        List<Transaction> toBeCreated = new ArrayList<Transaction>();
        for(RecurringTransaction r:recurring) {
            Transaction t = new Transaction();
            t.setBudget(r.getBudget());
            t.setAccount(r.getAccount());
            t.setAmount(r.getAmount());
            t.setType(r.getTransactionType());
            t.setName(r.getName());
            t.setCreatedBy(r.getCreatedBy());
            t.setDate(today);
            toBeCreated.add(t);
            r.setMostRecentDate(today);

        }
        recurringRepository.saveAll(recurring);
        transactionRepository.saveAll(toBeCreated);
    }

}
