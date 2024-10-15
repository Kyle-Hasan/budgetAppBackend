package com.kyle.budgetAppBackend.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public UserController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);

        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            return null;
        }

    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/signup")
    public String signup(HttpServletRequest request, HttpServletResponse response, @RequestBody SignupDto signupDto) {


        User user = userService.signup(signupDto);

        addUserToSecurityContext(request,response,signupDto.getUsername(),signupDto.getPassword());

        return "signed up";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginDto loginDto) {
        try {
            addUserToSecurityContext(request,response,loginDto.getUsername(),loginDto.getPassword());

            return ResponseEntity.ok("User authenticated successfully!");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication error");
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

    @GetMapping("/homescreen/{id}")
    public HomeScreenInfoDTO getHomeScreenInfo(@PathVariable Long id) {
        return userService.getHomeScreenInfo(id);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        request.getSession().invalidate();
        return "logged out";
    }

    private void addUserToSecurityContext(HttpServletRequest request, HttpServletResponse response,String username,String password) {

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();;

        SecurityContextHolder.setContext(context);
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, request, response);

    }

}
