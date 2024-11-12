package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.Token.TokenService;
import com.kyle.budgetAppBackend.role.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;  // Add TokenService for JWT handling

    public UserController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);
        return optionalUser.orElse(null);  // Simplified null check
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signup(@RequestBody SignupDto signupDto) {
        // Create user in DB
        User user = userService.signup(signupDto);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Authenticate and generate token
        String[] tokens = authenticateAndGenerateToken(signupDto.getUsername(), signupDto.getPassword());

        return ResponseEntity.ok(new TokenResponseDto(tokens[0],tokens[1]));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginDto loginDto) {
        try {
            // Authenticate and generate token
            String[] tokens = authenticateAndGenerateToken(loginDto.getUsername(), loginDto.getPassword());

            return ResponseEntity.ok(new TokenResponseDto(tokens[0],tokens[1]));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("")
    public User updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.update(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/budgetScreen")
    public HomeScreenInfoDTO getHomeScreenInfo(@RequestParam String startDate, @RequestParam String endDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        return user.map(value -> userService.getBudgetScreen(value.getId(),startDate,endDate)).orElse(null);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        tokenService.revokeAllUsernameTokens(username);
        SecurityContextHolder.clearContext();

        return "logged out";
    }

    @GetMapping("/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            List<String> roles = user.get().getRoles().stream().map(Role::getName).toList();
            return tokenService.generateToken(user.get(),roles,false);

        }
        else {
            tokenService.revokeAllUsernameTokens(username);
            SecurityContextHolder.clearContext();
            return null;
        }
    }


    private String[] authenticateAndGenerateToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);


        Optional<User> userOptional = userService.findByUsername(username);
        if(userOptional.isPresent()) {
            tokenService.revokeAllUsernameTokens(userOptional.get().getUsername());
            String refreshToken = tokenService.generateToken(userOptional.get(), new ArrayList<>(){},true);
            String accessToken = tokenService.generateToken(userOptional.get(),new ArrayList<>(){},false);
            String [] tokens = {refreshToken,accessToken};

            return tokens;

        }
        else {
            return null;
        }


    }
}
