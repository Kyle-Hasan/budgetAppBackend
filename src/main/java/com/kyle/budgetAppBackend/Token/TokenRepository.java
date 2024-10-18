package com.kyle.budgetAppBackend.Token;

import com.kyle.budgetAppBackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findByUser(User user);

    void deleteByToken(String token);

    @Query(value = """
            Select t.id from "tokens" as t JOIN users AS u
            ON (t.user_id = u.id) WHERE
            u.username=:username;
      """,nativeQuery = true)
    List<Long> findAllValidTokenIdByUser(@Param("username")String username);
}