package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.role.Role;
import com.kyle.budgetAppBackend.role.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User updateUser(UserUpdateDto userUpdateDto) {
        Optional<User> existingUser = userRepository.findById(userUpdateDto.getId());

        if(existingUser.isEmpty()) {
            return null;
        }

       var existingUserObj =  existingUser.get();

        existingUserObj.setEmail(userUpdateDto.getEmail());
        existingUserObj.setPassword(userUpdateDto.getUsername());

        if(userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            existingUserObj.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

       return userRepository.save(existingUserObj);
    }

    @Override
    public User signup(SignupDto signupDto) {
        User user = new User();

        user.setEmail(signupDto.getEmail());
        user.setUserName(signupDto.getUsername());
        Role userRole = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(userRole);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        userRepository.save(user);

        return user;

    }

    @Override
    public User login(LoginDto loginDto) {
            Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

            if(userOptional.isEmpty()) {
                return null;
            }

            if(!passwordEncoder.matches(loginDto.getPassword(),userOptional.get().getPassword())) {
                return null;
            }

            return userOptional.get();


    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
