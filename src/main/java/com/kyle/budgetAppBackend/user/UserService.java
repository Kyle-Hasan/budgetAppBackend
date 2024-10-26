package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.account.AccountRepository;
import com.kyle.budgetAppBackend.account.CurrentAccountDTO;
import com.kyle.budgetAppBackend.base.BaseService;
import com.kyle.budgetAppBackend.budget.BudgetGoalDTO;
import com.kyle.budgetAppBackend.budget.BudgetRepository;
import com.kyle.budgetAppBackend.role.Role;
import com.kyle.budgetAppBackend.role.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User> {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BudgetRepository budgetRepository;

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository baseRepository, RoleRepository roleRepository, BudgetRepository budgetRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        super(baseRepository);
        this.userRepository = baseRepository;
        this.roleRepository = roleRepository;
        this.budgetRepository = budgetRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;

    }


    @PreAuthorize("this.checkAuthorizationById(#id)")
    public User update(UserUpdateDto userUpdateDto) {
        Optional<User> existingUser = this.baseRepository.findById(userUpdateDto.getId());

        if(existingUser.isEmpty()) {
            return null;
        }

       var existingUserObj =  existingUser.get();

        if(userUpdateDto.getEmail() != null && !userUpdateDto.getEmail().isEmpty()) {
            existingUserObj.setEmail(userUpdateDto.getEmail());
        }

        if(userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isEmpty()) {
            existingUserObj.setUsername(userUpdateDto.getUsername());
        }

        if(userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            existingUserObj.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        if(userUpdateDto.getBudgets() != null) {
            var budgetsId = userUpdateDto.getBudgets().stream()
                    .map(budget -> {
                        return budget.getId();
                    })
                    .collect(Collectors.toList());

            var budgets = budgetRepository.findAllById(budgetsId);
            existingUserObj.setBudgets(budgets);
        }

        if(userUpdateDto.getAccounts() != null) {
            var accountsId = userUpdateDto.getAccounts().stream()
                    .map(budget -> {
                        return budget.getId();
                    })
                    .collect(Collectors.toList());

            var accounts = accountRepository.findAllById(accountsId);
            existingUserObj.setAccounts(accounts);
        }

       return this.baseRepository.save(existingUserObj);
    }


    public User signup(SignupDto signupDto) {
        User user = new User();

        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        Role userRole = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setCreatedBy(user);

        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        this.baseRepository.save(user);

        return user;

    }


    public User login(LoginDto loginDto) {
            Optional<User> userOptional = this.userRepository.findByUsername(loginDto.getUsername());

            if(userOptional.isEmpty()) {
                return null;
            }

            if(!passwordEncoder.matches(loginDto.getPassword(),userOptional.get().getPassword())) {
                return null;
            }

            return userOptional.get();


    }




    @PreAuthorize("this.checkAuthorizationById(#id)")
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    private String[] getStartAndEndMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.withDayOfMonth(1);
        LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.format(formatter);
        String endDateString = endDate.format(formatter);
        String[] dates = new String[2];
        dates[0] = startDateString;
        dates[1] = endDateString;
        return dates;


    }

    @PreAuthorize("this.checkAuthorizationById(#id)")
    public HomeScreenInfoDTO getBudgetScreen(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            var dates = getStartAndEndMonth();
            var budgetGoalObjs = budgetRepository.getBudgetGoals(dates[0],dates[1],user.getId());
            var currentAccountObjs = accountRepository.getCurrentAccounts(dates[0],dates[1],user.getId());
            List<BudgetGoalDTO> budgetGoalDTOs = budgetGoalObjs.stream().map(o -> new BudgetGoalDTO((Long) o[0], (String) o[1], (Double) o[2], (Double) o[3])).toList();
            List<CurrentAccountDTO> currentAccountDTOs = currentAccountObjs.stream().map(o -> new CurrentAccountDTO((Long) o[0], (String) o[1], (Double) o[2], (Double) o[3])).toList();
            Double budgetSpent = budgetGoalDTOs.stream().mapToDouble(b -> b.getCurrentSpent()).sum();
            Double accountsTotal = currentAccountDTOs.stream().mapToDouble(a -> a.getCurrentAccountBalance()).sum();
            HomeScreenInfoDTO h = new HomeScreenInfoDTO(currentAccountDTOs,budgetGoalDTOs,budgetSpent,accountsTotal);

            return h;
        }


        return null;

    }

    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }
}
