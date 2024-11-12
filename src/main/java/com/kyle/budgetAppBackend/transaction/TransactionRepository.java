package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction> {
    @Query("SELECT t FROM Transaction t WHERE t.createdBy.id = :userId AND t.date >= TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND t.date <= TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS')")
    public List<Transaction> getUserTransactions(@Param("userId")Long userId,@Param("startDate") String startDate,@Param("endDate") String endDate);
}
