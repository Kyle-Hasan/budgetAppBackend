package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.budget.BudgetService;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.transaction.TransactionForListDTO;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

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
        this.transactionRepository = transactionRepository;
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
            return Optional.ofNullable(super.updateFields(t, oldAccount.get()));
        }
        return Optional.empty();
    }

    public List<ParentEntity> getAccountSelections(Long userId) {
        var accountObjs = accountRepository.getAccountsUser(userId);
        return accountObjs.stream().map(o -> new ParentEntity((Long) o[0], (String) o[1])).toList();
    }

    private String[] getDateStrings() {
        LocalDateTime minDate = LocalDateTime.of(1700, 1, 1, 0, 0, 0);
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String minDateString = minDate.format(formatter);
        String todayString = today.format(formatter);
        String[] arr = new String[2];
        arr[0] = minDateString;
        arr[1] = todayString;
        return arr;
    }


    public CurrentAccountDTO getAccountInfo(Long userId, Long accountId) {
        String[] dateRanges = getDateStrings();
        var currentAccountObjs = accountRepository.getAccountUser(userId,accountId,dateRanges[0],dateRanges[1]);

        List<CurrentAccountDTO> currentAccountDTOs = currentAccountObjs.stream().map(o -> new CurrentAccountDTO((Long) o[0], (String) o[1], (Double) o[3], (Double) o[4],(String)o[2])).toList();
        if(currentAccountDTOs.isEmpty()) {
            return null;
        }
        else {
            return currentAccountDTOs.get(0);
        }
    }

    public List<CurrentAccountDTO> getAccountsInfo(Long userId,String startDate,String endDate) {
        //String[] dateRanges = getDateStrings();


        var currentAccountObjs = accountRepository.getCurrentAccounts(startDate,endDate,userId);
        List<CurrentAccountDTO> currentAccountDTOs = currentAccountObjs.stream().map(o -> new CurrentAccountDTO((Long) o[0], (String) o[1], (Double) o[3], (Double) o[4],(String)o[2])).toList();
        return  currentAccountDTOs;

    }

    public AccountPageDTO getAccountsPageDTO(Long userId,String startDate,String endDate)  {
        var currentAccountsDto = getAccountsInfo(userId,startDate,endDate);
        Double total = currentAccountsDto.stream().mapToDouble(CurrentAccountDTO::getAmountDeposited).sum();
        Double totalBalance = currentAccountsDto.stream().mapToDouble(CurrentAccountDTO::getCurrentAccountBalance).sum();
        return new AccountPageDTO(currentAccountsDto,total,totalBalance);
    }

    public static AccountDTO convertToDto(Account a) {
        List<TransactionForListDTO> transactionForListDTOS = BudgetService.convertTransactionsToDto(a.getTransactions(), a.getId(), a.getName());
        return new AccountDTO(a.getId(),a.getName(),a.getStartingBalance(), transactionForListDTOS,a.getIcon());
    }


}
