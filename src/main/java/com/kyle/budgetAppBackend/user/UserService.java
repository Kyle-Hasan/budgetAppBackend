package com.kyle.budgetAppBackend.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User updateUser(UserUpdateDto userUpdateDto);

    public User signup(SignupDto signupDto);

    public User login(LoginDto loginDto);

    public List<User> getAllUsers();

    public void deleteUser(Long id);

    public Optional<User> getUserById(Long id);


}
