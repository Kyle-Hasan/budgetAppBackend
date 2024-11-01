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
            "    COALESCE(SUM(t.amount), 0) AS totalSpent \n" +
            "FROM " +
            "    budgets AS b \n" +
            "LEFT JOIN " +
            "    transactions AS t ON b.id = t.budget_id \n" +
            "WHERE " +
            "    b.user_id = :userId \n" +
            "AND " +
            "    t.created_at >= TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') AND t.created_at <= TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS') \n" +
            "GROUP BY " +
            "    b.id, b.name, b.amount;", nativeQuery = true)
    ArrayList<Object[]> getBudgetGoals(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId);
}
