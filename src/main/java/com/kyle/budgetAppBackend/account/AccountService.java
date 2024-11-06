package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import com.kyle.budgetAppBackend.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AccountService extends BaseService<Account> {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }


    public Optional<Account> updateChangedOnly (Account t) {
        Optional<Account> oldAccount = baseRepository.findById(t.getId());
        if (oldAccount.isPresent()) {
            var transactionsIds = t.getTransactions().stream()
                    .map(transaction -> {
                        return transaction.getId();
                    })
                    .collect(Collectors.toList());

            var transactions = transactionRepository.findAllById(transactionsIds);
            t.setTransactions(transactions);
            return Optional.ofNullable(super.updateChangedOnly(t, oldAccount.get()));
        }
        return Optional.empty();
    }

    public List<ParentEntity> getAccountSelections(Long userId) {
        var accountObjs = accountRepository.getAccountsUser(userId);
        return accountObjs.stream().map(o -> new ParentEntity((Long) o[0], (String) o[1])).toList();
    }


    public CurrentAccountDTO getAccountInfo(Long userId, Long accountId) {
        LocalDateTime minDate = LocalDateTime.of(-4713, 1, 1, 0, 0, 0);
        LocalDateTime today = LocalDateTime.now();
        var currentAccountObjs = accountRepository.getAccountUser(userId,accountId,minDate.toString(),today.toString());

        List<CurrentAccountDTO> currentAccountDTOs = currentAccountObjs.stream().map(o -> new CurrentAccountDTO((Long) o[0], (String) o[1], (Double) o[2], (Double) o[3])).toList();
        if(currentAccountDTOs.isEmpty()) {
            return null;
        }
        else {
            return currentAccountDTOs.get(0);
        }
    }

    public List<CurrentAccountDTO> getAccountsInfo(Long userId) {
        LocalDateTime minDate = LocalDateTime.of(1700, 1, 1, 0, 0, 0);
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String minDateString = minDate.format(formatter);
        String todayString = today.format(formatter);

        var currentAccountObjs = accountRepository.getCurrentAccounts(minDateString,todayString,userId);
        List<CurrentAccountDTO> currentAccountDTOs = currentAccountObjs.stream().map(o -> new CurrentAccountDTO((Long) o[0], (String) o[1], (Double) o[2], (Double) o[3])).toList();
        return  currentAccountDTOs;

    }


}
