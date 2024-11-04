package com.kyle.budgetAppBackend.transaction;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
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
}
