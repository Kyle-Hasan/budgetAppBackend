package com.kyle.budgetAppBackend.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        Optional<User> optionalUser = userService.getById(id);

        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            return null;
        }

    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
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

        return userService.update(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        userService.delete(id);
    }

}
