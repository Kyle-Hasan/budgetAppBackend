package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.base.VirtualScrollRequest;
import com.kyle.budgetAppBackend.user.HomeScreenInfoDTO;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController extends BaseController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        super(userService);
        this.transactionService = transactionService;
    }


    @GetMapping("/{id}")
    public TransactionForListDTO getTransaction(@PathVariable Long id) {
        var transactionOptional =  transactionService.get(id);
        if(transactionOptional.isPresent()){
            return TransactionService.convertTransactionToDto(transactionOptional.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @PostMapping("")
    public TransactionForListDTO createTransaction(@RequestBody Transaction transaction) {
        return TransactionService.convertTransactionToDto(transactionService.create(transaction));
    }

    @PutMapping("")
    public TransactionForListDTO saveTransaction(@RequestBody Transaction transaction) {
        var oldTransaction = transactionService.get(transaction.getId());

        return TransactionService.convertTransactionToDto(oldTransaction.map(value -> transactionService.updateChangedOnly(transaction, value)).orElse(null));


    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @GetMapping("")
    public List<Transaction> getTransactions() {
        return transactionService.getAll();
    }

    @GetMapping("userTransactions")
    public TransactionPageResponse getTransactionDtoUsers(@RequestParam String startDate, @RequestParam String endDate,@ModelAttribute VirtualScrollTransactions virtualScrollRequest) {
        Long userId= getUserId();
        if(userId != null) {
            return transactionService.getTransactionPage(userId, startDate, endDate,virtualScrollRequest);
        }
        else {
            return null;
        }

    }

    @GetMapping("/searchByName")
    public TransactionPageResponse getSearchByTime(@RequestParam String name, @RequestParam String startDate,
                                             @RequestParam String endDate, @ModelAttribute VirtualScrollTransactions virtualScrollRequest) {

        Long userId = getUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {

            var retVal= transactionService.getByName(userId,startDate,endDate,virtualScrollRequest.getSort(),
                    virtualScrollRequest.getOrder(),virtualScrollRequest.getSize(),virtualScrollRequest.getPageNumber(),name, virtualScrollRequest.getFilter());
            return retVal;
        }

    }


}
