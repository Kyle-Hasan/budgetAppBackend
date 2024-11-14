package com.kyle.budgetAppBackend.recurringTransaction;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RecurringRepository extends BaseRepository<RecurringTransaction> {
    @Query("SELECT r from RecurringTransaction r WHERE r.createdBy.id = :userId")
    List<RecurringTransaction> getUserTransactions(@Param("userId")Long userId);

}
