package com.kyle.budgetAppBackend.base;

import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public abstract class BaseController<T extends BaseEntity> {
    public UserService userService;
    public BaseController(UserService userService) {
        this.userService = userService;
    }
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = this.userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            return null;
        }
        else {
            User user = userOptional.get();
            return user;
        }
    }


}
