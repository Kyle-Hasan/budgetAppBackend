package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.transaction.*;
import com.kyle.budgetAppBackend.user.HomeScreenInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService extends BaseService<Budget> {
    private TransactionRepository transactionRepository;
    private BudgetRepository budgetRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public BudgetService(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        super(budgetRepository);
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    public Optional<Budget> updateChangedOnly(Budget t) {
        Optional<Budget> oldBudget = baseRepository.findById(t.getId());
        if (oldBudget.isPresent()) {
            var transactionsIds = t.getTransactions().stream()
                    .map(transaction -> {
                        return transaction.getId();
                    })
                    .collect(Collectors.toList());

            var transactions = transactionRepository.findAllById(transactionsIds);
            t.setTransactions(transactions);
            return Optional.ofNullable(super.updateFields(t, oldBudget.get()));
        }
        return Optional.empty();
    }

    @Override
    public Budget create(Budget budget) {

        // check if any new transactions exist
        List<Transaction> uncreated = budget.getTransactions().stream().filter(t -> t.getId() == -1).toList();
        Budget retVal = super.create(budget);

        return retVal;
    }

    public List<ParentEntity> budgetSelectionsUser(Long userId) {
        var budgetObjs= budgetRepository.getBudgetsUser(userId);

        return budgetObjs.stream().map(o -> new ParentEntity((Long) o[0], (String) o[1])).toList();
    }

    public static List<TransactionForListDTO> convertTransactionsToDto(List<Transaction> transactions, long budgetId,String budgetName) {
        ParentEntity budget = new ParentEntity();
        budget.setName(budgetName);
        budget.setId(budgetId);
        return transactions.stream().map(TransactionService::convertTransactionToDto).toList();
    }

    public static BudgetDTO convertBudgetToDto(Budget budget) {
        List<TransactionForListDTO> transactionForListDTOS = convertTransactionsToDto(budget.getTransactions(), budget.getId(), budget.getName());

        return new BudgetDTO(budget.getId(), budget.getName(),  budget.getAmount(), transactionForListDTOS,budget.getIcon());
    }

    public List<Object[]> findBudgetsWithDynamicQuery(
            Long userId,
            String startDate,
            String endDate,
            String sortField,
            String sortOrder,
            int size,
            int pageNumber,
            String searchName) {

        StringBuilder queryBuilder = new StringBuilder("SELECT b.id AS budgetId, ")
                .append("b.name AS budgetName, ")
                .append("b.icon AS icon, ")
                .append("b.amount AS budgetAmount, ")
                .append("COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ")
                .append("WHEN t.type = 'INCOME' THEN 0 ELSE 0 END), 0) AS totalSpent ")
                .append("FROM budgets AS b ")
                .append("LEFT JOIN transactions AS t ON (b.id = t.budget_id ")
                .append("AND t.date >= TO_TIMESTAMP(:startDate, 'YYYY-MM-DD HH24:MI:SS') ")
                .append("AND t.date <= TO_TIMESTAMP(:endDate, 'YYYY-MM-DD HH24:MI:SS')) ")
                .append("WHERE b.user_id = :userId ");

        if (searchName != null && !searchName.trim().isEmpty()) {
            queryBuilder.append("AND LOWER(b.name) LIKE LOWER(:searchName) ");
        }

        queryBuilder.append("GROUP BY b.id, b.name, b.amount, b.icon ");

        if (sortField != null && sortOrder != null) {
            String sortFieldResolved = "";
            if ("name".equals(sortField)) {
                sortFieldResolved = "b." + sortField;
            } else if ("totalAmount".equals(sortField)) {
                sortFieldResolved = "b.amount";
            } else if ("amountSpent".equals(sortField)) {
                sortFieldResolved = "totalSpent";
            }
            queryBuilder.append("ORDER BY ").append(sortFieldResolved).append(" ").append(sortOrder).append(", b.id ");
        }

        int offset = pageNumber * size;
        queryBuilder.append("LIMIT :size OFFSET :offset");

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("size", size);
        query.setParameter("offset", offset);

        if (searchName != null && !searchName.trim().isEmpty()) {
            query.setParameter("searchName", "%" + searchName.trim() + "%");
        }

        return query.getResultList();
    }


    public double getBudgetTotalSpent(Long userId,
                                      String startDate,
                                      String endDate) {
        Object[] sumObj = budgetRepository.getBudgetSum(startDate,endDate,userId);
        double amount = (double) sumObj[0];
        return Math.round(amount * 100.0) / 100.0;
    }

    public static Budget convertDtoToBudget(BudgetDTO dto) {
            Budget budget = new Budget();
            List<Transaction> transactions = dto.transactions.stream().map(t-> {
                Transaction newT = new Transaction();
                newT.setId(t.getId());
                return newT;
            }).toList();

            return budget;
    }

    public List<Budget> findAllById(List<Long> budgetsId) {
        return budgetRepository.findAllById(budgetsId);
    }

    public HomeScreenInfoDTO getByName(Long userId,
                                       String startDate,
                                       String endDate,
                                       String sortField,
                                       String sortOrder,
                                       int size,
                                       int pageNumber,
                                       String searchName) {

        List<Object[]> objects = findBudgetsWithDynamicQuery(userId,startDate,endDate,sortField,sortOrder,size,pageNumber,searchName);
        List<BudgetGoalDTO> budgetGoalDTOs = objects.stream().map(o -> new BudgetGoalDTO((Long) o[0], (String) o[1], (Double) o[3], (Double) o[4],(String)o[2])).toList();
        Double totalSpent = budgetGoalDTOs.stream().mapToDouble(BudgetGoalDTO::getCurrentSpent).sum();
        return new HomeScreenInfoDTO(null,budgetGoalDTOs,totalSpent,null);

    }



}
