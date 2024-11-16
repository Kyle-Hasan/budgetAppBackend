package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


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

    @PatchMapping("")
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
    public TransactionPageResponse getTransactionDtoUsers(@RequestParam String startDate, @RequestParam String endDate) {
        User user = getUser();
        if(user != null) {
            return transactionService.getTransactionPage(user.getId(), startDate, endDate);
        }
        else {
            return null;
        }

    }


}
