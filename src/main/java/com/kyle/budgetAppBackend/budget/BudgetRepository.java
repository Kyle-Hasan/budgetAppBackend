package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseRepository;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BudgetRepository extends BaseRepository<Budget> {
    @Query(value = "SELECT " +
            "    b.id AS budgetId, " +
            "    b.name AS budgetName, " +
            "    b.amount AS budgetAmount, " +
            "    COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount WHEN t.type = 'INCOME' THEN 0 ELSE 0 END), 0) AS totalSpent \n" +
            "FROM " +
            "    budgets AS b \n" +
            "LEFT JOIN " +
            "    transactions AS t ON (b.id = t.budget_id AND t.date >= TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND t.date <= TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS'))\n" +
            "WHERE " +
            "    b.user_id = :userId \n" +
            "GROUP BY " +
            "    b.id, b.name, b.amount;", nativeQuery = true)
    ArrayList<Object[]> getBudgetGoals(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId);


    @Query(value = "SELECT DISTINCT " +
    "    b.id as id," +
    "    b.name as name \n" +
    " FROM budgets AS b \n" +
    " JOIN " +
    " users as u on b.user_id = :userId;",nativeQuery = true
     )
    ArrayList<Object[]> getBudgetsUser(@Param("userId") Long userId);
}
