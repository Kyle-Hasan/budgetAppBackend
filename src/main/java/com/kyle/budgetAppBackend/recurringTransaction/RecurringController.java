package com.kyle.budgetAppBackend.recurringTransaction;


import com.kyle.budgetAppBackend.base.BaseController;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/recurring")
public class RecurringController extends BaseController {
    private RecurringService recurringService;

    public RecurringController(UserService userService, RecurringService recurringService) {
        super(userService);
        this.recurringService = recurringService;
    }

    @DeleteMapping("/{id}")
    public void deleteRecurring(@PathVariable Long id) {
        recurringService.delete(id);
    }

    @PostMapping("")
    public RecurringTransaction createRecurring(@RequestBody RecurringTransaction r) {
        return recurringService.create(r);
    }

    @PutMapping("")
    public RecurringTransaction updateRecurring(@RequestBody RecurringTransaction r) {
        return recurringService.update(r);
    }

    @GetMapping("/{id}")
    public RecurringTransactionDTO getRecurring(@PathVariable Long id) {
        var optional = recurringService.get(id);
        if(optional.isPresent()) {
            return RecurringService.convertToDTO(optional.get());
        }
        else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found");
        }
    }

    @GetMapping("")
    public List<RecurringTransactionDTO> getAllForUser(){
        Long userId = getUserId();
        if(userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        else {
            return recurringService.getRecurringTransactionsForUser(userId).stream().map(RecurringService::convertToDTO).toList();
        }
    }
}
