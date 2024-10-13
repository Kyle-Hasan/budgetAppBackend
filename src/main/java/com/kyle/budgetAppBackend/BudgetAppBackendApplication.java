package com.kyle.budgetAppBackend;

import com.kyle.budgetAppBackend.account.Account;
import com.kyle.budgetAppBackend.account.AccountRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication()
public class BudgetAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetAppBackendApplication.class, args);
	}


	/*@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, TransactionRepository transactionRepository, BudgetRepository budgetRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		return runner -> {
			generateData(userRepository,transactionRepository,budgetRepository,accountRepository,passwordEncoder);
		};
	}

	private void generateData(UserRepository userRepository, TransactionRepository transactionRepository, BudgetRepository budgetRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setUsername("reona");
		user.setPassword(passwordEncoder.encode("password"));
		user.setEmail("email5@example.com");
		List<Account> accounts = new ArrayList<Account>();
		for(int i = 0; i < 3; i++) {

			Account account = new Account();
			account.setName("Account " + i);
			account.setBalance((double) (16*i));
			List<Transaction> transactionList = new ArrayList<Transaction>();
			for(int j = 0 ; j < 3; j++) {
				Transaction t = new Transaction();
				t.setName("Transaction " + i + j + " account");
				t.setAmount(j + i * 25);
				t.setAccount(account);
				transactionList.add(t);


			}
			account.setUser(user);
			account.setTransactions(transactionList);
			accounts.add(account);


		}
		List<Budget> budgets = new ArrayList<Budget>();
		for(int i = 0; i < 3; i++) {
			Budget budget = new Budget();
			budget.setName("budget " + i);
			budget.setAmount((double) (80*i));
			List<Transaction> transactionList = new ArrayList<Transaction>();
			for(int j = 0 ; j < 3; j++) {
				Transaction t = new Transaction();
				t.setName("Transaction " + i + j + " budget");
				t.setAmount(j + i * 10);
				t.setBudget(budget);
				transactionList.add(t);

			}
			budget.setTransactions(transactionList);
			budget.setUser(user);
			budgets.add(budget);

		}
		user.setBudgets(budgets);
		user.setAccounts(accounts);
		userRepository.save(user);
	}*/

}
