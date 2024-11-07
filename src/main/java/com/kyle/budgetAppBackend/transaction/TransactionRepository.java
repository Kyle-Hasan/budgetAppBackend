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
    @Query("SELECT t from Transaction t WHERE t.createdBy.id=:userId")
    public List<Transaction> getUserTransactions(@Param("userId")Long userId);
}
