package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BudgetService extends BaseService<Budget> {

    public BudgetService(BaseRepository<Budget> baseRepository) {
        super(baseRepository);
    }
}
