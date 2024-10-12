package com.kyle.budgetAppBackend;

import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.budget.BudgetRepository;
import com.kyle.budgetAppBackend.transaction.Transaction;
import com.kyle.budgetAppBackend.transaction.TransactionRepository;
import com.kyle.budgetAppBackend.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import java.util.Date;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BudgetAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetAppBackendApplication.class, args);
	}


/*	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
		return runner -> {
			generateData(userRepository,transactionRepository,budgetRepository);
		};
	}

	private void generateData(UserRepository userRepository, TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setEmail("email");
		Budget budget = new Budget();
		budget.setAmount(10);
		budget.setName("budgetName");
		Date currentDate = new Date();
		Transaction transaction = new Transaction();
		transaction.setName("new transaction");
		transaction.setAmount(2);
		transaction.setCreatedAt(currentDate);

		user.addBudget(budget);
		budget.addTransaction(transaction);
		userRepository.save(user);
		transactionRepository.save(transaction);
		budgetRepository.save(budget);
	}*/

}
