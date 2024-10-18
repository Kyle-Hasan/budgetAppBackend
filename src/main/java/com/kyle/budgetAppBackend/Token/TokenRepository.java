package com.kyle.budgetAppBackend.Token;

import com.kyle.budgetAppBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findByUser(User user);

    void deleteByToken(String token);

    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.username = u.username\s
      where u.username = :username and (t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(String username);
}