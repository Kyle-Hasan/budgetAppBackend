package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionService extends BaseService<Transaction> {


    public TransactionService(BaseRepository<Transaction> baseRepository) {
        super(baseRepository);
    }
}
