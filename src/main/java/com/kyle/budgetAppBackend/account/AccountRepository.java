package com.kyle.budgetAppBackend.account;

import com.kyle.budgetAppBackend.base.BaseRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    @Query(value = "SELECT " +
            "    a.id AS accountId, " +
            "    a.name AS accountName, " +
            "    a.balance + COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN -t.amount WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), 0) AS currentAccountBalance, " +
            "    COALESCE(SUM(t.amount), 0) AS amountDeposited\n" +
            "FROM \n" +
            "    accounts AS a\n" +
            "LEFT JOIN \n" +
            "    transactions AS t ON a.id = t.account_id\n" +
            "WHERE " +
            "    t.created_at >= TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND t.created_at <= TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS')\n" +
            "AND\n" +
            "a.user_id = :userId \n" +
            "GROUP BY " +
            "    a.id, a.name, a.balance;",nativeQuery = true)
    ArrayList<Object[]> getCurrentAccounts(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId);

    @Query(value = "SELECT DISTINCT " +
    " a.id as accountId, " +
    " a.name as accountName " +
    " FROM \n" +
    " accounts as a\n" +
    "JOIN \n" +
    "users as u ON (a.user_id = :userId);", nativeQuery = true)
    ArrayList<Object[]> getAccountsUser(@Param("userId") Long userId);
}
