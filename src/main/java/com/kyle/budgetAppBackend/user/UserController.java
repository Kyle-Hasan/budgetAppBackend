package com.kyle.budgetAppBackend.user;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            return null;
        }

    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignupDto signupDto) {
        return userService.signup(signupDto);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("")
    public User updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
