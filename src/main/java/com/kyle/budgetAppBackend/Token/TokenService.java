package com.kyle.budgetAppBackend.Token;

import com.kyle.budgetAppBackend.user.User;
import com.kyle.budgetAppBackend.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenService {

    @Value("${app.jwtKey}")
    private String SECRET_KEY;

    @Value("${app.refreshTokenExpiration}")
    private Long refreshTokenExpiration;


    @Value("${app.accessTokenExpiration}")
    private Long accessTokenExpiration;



    private TokenRepository tokenRepository;


    private UserRepository userRepository;

    public TokenService(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user, List<String> roles, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",roles);
        return createToken(claims, user, isRefreshToken);
    }

    private String createToken(Map<String, Object> claims, User user, boolean isRefreshToken) {
        long expirationTime = isRefreshToken ? refreshTokenExpiration : accessTokenExpiration; // 10 hours for access token, 30 days for refresh token
        String token = Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();


        Token tokenEntity = new Token();
        tokenEntity.setUser(user);
        tokenEntity.setToken(token);
        tokenEntity.setRevoked(false);
        tokenEntity.setRefreshToken(isRefreshToken);
        tokenRepository.save(tokenEntity);

        return token;
    }

    public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);  // Extract the username from the token

        boolean tokenExists = tokenRepository.findByToken(token).isPresent();
        boolean isRevoked = tokenRepository.findByToken(token).map(Token::isRevoked).orElse(true);

        return (extractedUsername.equals(username) && !isTokenExpired(token) && tokenExists && !isRevoked);
    }



    public void deleteAllOfUsernameTokens(String username) {
        List<Long> tokenIds = tokenRepository.findAllValidTokenByUser(username).stream().map(t -> t.getId()).toList();
        tokenRepository.deleteAllById(tokenIds);
    }

    public void revokeAllUsernameTokens(String username) {
        List<Long> tokenIds = tokenRepository.findAllValidTokenByUser(username).stream().map(t -> t.getId()).toList();
        List<Token> tokens = tokenRepository.findAllById(tokenIds);
        tokens.stream().forEach(t-> t.setRevoked(true));
        tokenRepository.saveAll(tokens);

    }


    public void revokeToken(String token) {
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            tokenRepository.save(t);
        });
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}

