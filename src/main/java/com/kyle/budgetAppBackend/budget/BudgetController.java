package com.kyle.budgetAppBackend.budget;

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
public class BudgetController {
    private BudgetService budgetService;
    private UserService userService;
    public BudgetController(BudgetService budgetService,UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
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

    @PatchMapping("")
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = this.userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {
            User user = userOptional.get();
            return budgetService.budgetSelectionsUser(user.getId());
        }

    }

    @GetMapping("/searchByName")
    public HomeScreenInfoDTO getSearchByTime(@RequestParam String name, @RequestParam String startDate,
                                             @RequestParam String endDate, @ModelAttribute VirtualScrollRequest virtualScrollRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = this.userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {
            User user = userOptional.get();
            var retVal= budgetService.getByName(user.getId(),startDate,endDate,virtualScrollRequest.getSort(),
                    virtualScrollRequest.getOrder(),virtualScrollRequest.getSize(),virtualScrollRequest.getPageNumber(),name);
            return retVal;
        }

    }
}
