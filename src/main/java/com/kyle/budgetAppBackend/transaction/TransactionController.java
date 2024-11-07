package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.web.bind.annotation.*;
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
    public Transaction getTransaction(@PathVariable Long id) {
        var transactionOptional =  transactionService.get(id);
        if(transactionOptional.isPresent()){
            return transactionOptional.get();
        }
        else {
            return null;
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

    @DeleteMapping("")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @GetMapping("")
    public List<Transaction> getTransactions() {
        return transactionService.getAll();
    }

    @GetMapping("userTransactions")
    public List<TransactionForListDTO> getTransactionDtoUsers() {
        User user = getUser();
        return transactionService.getUserTransactions(user.getId());
    }


}
