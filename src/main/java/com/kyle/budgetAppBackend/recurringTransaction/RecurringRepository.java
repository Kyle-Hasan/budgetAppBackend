package com.kyle.budgetAppBackend.recurringTransaction;

import com.kyle.budgetAppBackend.base.BaseRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


import java.util.List;


@Repository
public interface RecurringRepository extends BaseRepository<RecurringTransaction> {
    @Query("SELECT r from RecurringTransaction r WHERE r.createdBy.id = :userId")
    List<RecurringTransaction> getUserTransactions(@Param("userId")Long userId);

    @Query("SELECT r FROM RecurringTransaction r WHERE " +
            "(r.frequency = :daily) OR " +
            "(r.frequency = :weekly AND r.mostRecentDate <= :lastWeek) OR " +
            "(r.frequency = :biweekly AND r.mostRecentDate <= :twoWeeksAgo) OR " +
            "(r.frequency = :monthly AND r.mostRecentDate <= :lastMonth) OR " +
            "(r.mostRecentDate IS NULL)")
    List<RecurringTransaction> getRecurringForUpdate(
            @Param("lastWeek") LocalDate lastWeek,
            @Param("twoWeeksAgo") LocalDate twoWeeksAgo,
            @Param("lastMonth") LocalDate lastMonth,
            @Param("daily") FrequencyEnum daily,
            @Param("weekly") FrequencyEnum weekly,
            @Param("biweekly") FrequencyEnum biweekly,
            @Param("monthly") FrequencyEnum monthly
    );


}
