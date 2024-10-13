package com.kyle.budgetAppBackend.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User update(UserUpdateDto userUpdateDto);

    public User signup(SignupDto signupDto);

    public User login(LoginDto loginDto);

    public List<User> getAllUsers();

    public void delete(Long id);

    public Optional<User> getById(Long id);

    public HomeScreenInfoDTO getHomeScreenInfo(Long id);


}
