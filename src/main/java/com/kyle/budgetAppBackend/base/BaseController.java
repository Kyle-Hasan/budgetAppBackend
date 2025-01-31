package com.kyle.budgetAppBackend.base;

import com.kyle.budgetAppBackend.security.CustomUserDetails;

import com.kyle.budgetAppBackend.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public abstract class BaseController<T extends BaseEntity> {
    public UserService userService;
    public BaseController(UserService userService) {
        this.userService = userService;
    }


    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        if(details == null) {
            return null;
        }
        return details.getUserId();
    }


}
