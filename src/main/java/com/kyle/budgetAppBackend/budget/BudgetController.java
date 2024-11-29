package com.kyle.budgetAppBackend.budget;

import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.base.VirtualScrollRequest;
import com.kyle.budgetAppBackend.budget.Budget;
import com.kyle.budgetAppBackend.transaction.ParentEntity;
import com.kyle.budgetAppBackend.user.HomeScreenInfoDTO;
import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController extends BaseController {
    private BudgetService budgetService;

    public BudgetController(BudgetService budgetService,UserService userService) {
        super(userService);
        this.budgetService = budgetService;

    }

    @GetMapping("/{id}")
    public BudgetDTO getBudget(@PathVariable Long id) {
        var budgetOptional =  budgetService.get(id);
        if(budgetOptional.isPresent()){
            return BudgetService.convertBudgetToDto(budgetOptional.get());
        }
        else {
            return null;
        }
    }

    @PostMapping(value = "")
    public BudgetDTO createBudget(@RequestBody Budget budget ) {
        return BudgetService.convertBudgetToDto(budgetService.create(budget));
    }

    @PutMapping("")
    public Budget saveBudget(@RequestBody Budget budget) {
        var budgetOptional =  budgetService.updateChangedOnly(budget);
        if(budgetOptional.isPresent()){
            return budgetOptional.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurring Transaction not found");

        }
    }

    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.delete(id);
    }



    @GetMapping("/budgetSelections")
    public List<ParentEntity> getBudgetSelections() {

        Long userId = getUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {

            return budgetService.budgetSelectionsUser(userId);
        }

    }

    @GetMapping("/searchByName")
    public HomeScreenInfoDTO getSearchByTime(@RequestParam String name, @RequestParam String startDate,
                                             @RequestParam String endDate, @ModelAttribute VirtualScrollRequest virtualScrollRequest) {
        Long userId = getUserId();


        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {

            var retVal= budgetService.getByName(userId,startDate,endDate,virtualScrollRequest.getSort(),
                    virtualScrollRequest.getOrder(),virtualScrollRequest.getSize(),virtualScrollRequest.getPageNumber(),name);
            return retVal;
        }

    }
}
