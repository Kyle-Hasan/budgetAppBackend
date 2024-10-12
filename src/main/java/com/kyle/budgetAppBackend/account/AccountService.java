package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;

public class AccountService extends BaseService<Account> {
    public AccountService(BaseRepository<Account> baseRepository) {
        super(baseRepository);
    }
}
