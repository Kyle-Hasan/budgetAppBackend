package com.kyle.budgetAppBackend.budget;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
