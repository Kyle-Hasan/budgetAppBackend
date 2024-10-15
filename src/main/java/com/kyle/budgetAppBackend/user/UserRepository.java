package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
}
