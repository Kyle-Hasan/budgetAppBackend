package com.kyle.budgetAppBackend.base;

import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserRepository;
import jakarta.persistence.PrePersist;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreatedByListener {

    private UserRepository userRepository;

    public CreatedByListener(@Lazy UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @PrePersist
    public void setCreatedBy(BaseEntity entity) {
        if(entity.getCreatedBy() != null) {
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            entity.setCreatedBy(user.get());
        }
    }
}
