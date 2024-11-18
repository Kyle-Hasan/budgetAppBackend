package com.kyle.budgetAppBackend.transaction;

import com.kyle.budgetAppBackend.base.BaseRepository;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.base.VirtualScrollRequest;
import com.kyle.budgetAppBackend.budget.BudgetGoalDTO;
import com.kyle.budgetAppBackend.user.HomeScreenInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class TransactionService extends BaseService<Transaction> {

    @PersistenceContext
    private EntityManager entityManager;

    private TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
    }

    public static TransactionForListDTO convertTransactionToDto(Transaction t) {
        ParentEntity account = null;
        ParentEntity budget = null;
        var fullAccount = t.getAccount();
        var fullBudget = t.getBudget();
        if(fullBudget != null) {
            budget = new ParentEntity(fullBudget.getId(),fullBudget.getName());
        }

        if(fullAccount != null) {
            account = new ParentEntity(fullAccount.getId(),fullAccount.getName());
        }
        String type = null;
        if(t.getType() != null) {
            type = t.getType().toString();
        }
        return new TransactionForListDTO(t.getId(),t.getAmount(),t.getName(),t.getDate(),account,budget,type);

    }

    public List<Transaction> findTransactionsWithDynamicQuery(
            Long userId,
            String startDate,
            String endDate,
            String sortField,
            String sortOrder,
            int size,
            int pageNumber,
            String searchName, TransactionType filter) {

        StringBuilder queryStr = new StringBuilder(
                "SELECT t " +
                        "FROM Transaction t " +
                        "WHERE t.createdBy.id = :userId " +
                        "AND t.date >= TO_DATE(:startDate, 'YYYY-MM-DD') " +
                        "AND t.date <= TO_DATE(:endDate, 'YYYY-MM-DD') "
        );


        if (searchName != null && !searchName.trim().isEmpty()) {
            queryStr.append("AND LOWER(t.name) LIKE LOWER(:searchName) ");
        }

        if(filter != null) {

            queryStr.append("AND t.type=:filter ");

        }


        if (sortField != null && sortOrder != null) {
            String sortFieldResolved = "";
            switch (sortField) {
                case "name":
                    sortFieldResolved = "t.name";
                    break;
                case "amount":
                    sortFieldResolved = "t.amount";
                    break;
                case "date":
                    sortFieldResolved = "t.date";
                    break;
                case "type":
                    sortFieldResolved = "t.type";
                    break;
            }
            queryStr.append("ORDER BY ").append(sortFieldResolved).append(" ").append(sortOrder).append(" ");
        }

        int offset = pageNumber * size;

        TypedQuery<Transaction> query = entityManager.createQuery(queryStr.toString(), Transaction.class);
        query.setParameter("userId", userId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        if (searchName != null && !searchName.trim().isEmpty()) {
            query.setParameter("searchName", "%" + searchName.trim() + "%");
        }
        if(filter != null) {
            query.setParameter("filter",filter);
        }

        query.setFirstResult(offset); // Offset for pagination
        query.setMaxResults(size);    // Limit for pagination

        return query.getResultList();
    }


    public List<TransactionForListDTO> getUserTransactions(Long userId, String startDate, String endDate, VirtualScrollTransactions virtualScrollRequest) {
        List<Transaction> transactions = findTransactionsWithDynamicQuery(userId,startDate,endDate,virtualScrollRequest.getSort(),virtualScrollRequest.getOrder(),virtualScrollRequest.getSize(),
                virtualScrollRequest.getPageNumber(),null, virtualScrollRequest.getFilter());
        return transactions.stream().map(TransactionService::convertTransactionToDto).toList();
    }

    public TransactionPageResponse getTransactionPage(Long userId,String startDate, String endDate,VirtualScrollTransactions virtualScrollRequest) {
        List<TransactionForListDTO> transactionForListDTOS = getUserTransactions(userId,startDate,endDate,virtualScrollRequest);
        var retVal = createResponse(transactionForListDTOS,startDate,endDate,userId);
        if(virtualScrollRequest.getFilter() == TransactionType.INCOME) {
            retVal.setTotalSpent((double) 0);
        }
        else if(virtualScrollRequest.getFilter() == TransactionType.EXPENSE) {
            retVal.setTotalDeposited((double)0);
        }
        return retVal;
    }

    public TransactionPageResponse createResponse(List<TransactionForListDTO> transactionForListDTOS,String startDate,String endDate,long userId) {
        List<Object[]> objectArr = transactionRepository.getUserTransactionSums(userId,startDate,endDate);
        Double totalSpent = (Double) objectArr.get(0)[0];
        totalSpent = Math.round(totalSpent * 100.0) / 100.0;
        Double totalDeposited = (Double) objectArr.get(0)[1];
        totalDeposited = Math.round(totalDeposited * 100.0)/100.0;
        return new TransactionPageResponse(transactionForListDTOS,totalSpent,totalDeposited);
    }


    public TransactionPageResponse getByName(Long userId,
                                       String startDate,
                                       String endDate,
                                       String sortField,
                                       String sortOrder,
                                       int size,
                                       int pageNumber,
                                       String searchName, TransactionType filter) {

        var transactions = findTransactionsWithDynamicQuery(userId,startDate,endDate,sortField,sortOrder,size,pageNumber,searchName,filter);
        List<TransactionForListDTO> transactionForListDTOS = transactions.stream().map(TransactionService::convertTransactionToDto).toList();
        return createResponse(transactionForListDTOS,startDate,endDate,userId);
    }

}
