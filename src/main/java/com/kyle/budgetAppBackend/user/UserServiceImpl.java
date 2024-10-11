package com.kyle.budgetAppBackend.user;

import com.kyle.budgetAppBackend.role.Role;
import com.kyle.budgetAppBackend.role.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }


    @Override
    public User update(UserUpdateDto userUpdateDto) {
        Optional<User> existingUser = userRepository.findById(userUpdateDto.getId());

        if(existingUser.isEmpty()) {
            return null;
        }

       var existingUserObj =  existingUser.get();

        existingUserObj.setEmail(userUpdateDto.getEmail());
        existingUserObj.setPassword(userUpdateDto.getUsername());

        if(userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            existingUserObj.setPassword(passwordEncoder().encode(userUpdateDto.getPassword()));
        }

       return userRepository.save(existingUserObj);
    }

    @Override
    public User signup(SignupDto signupDto) {
        User user = new User();

        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        Role userRole = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(userRole);
        user.setRoles(roles);

        user.setPassword(passwordEncoder().encode(signupDto.getPassword()));

        userRepository.save(user);

        return user;

    }

    @Override
    public User login(LoginDto loginDto) {
            Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

            if(userOptional.isEmpty()) {
                return null;
            }

            if(!passwordEncoder().matches(loginDto.getPassword(),userOptional.get().getPassword())) {
                return null;
            }

            return userOptional.get();


    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }
}
