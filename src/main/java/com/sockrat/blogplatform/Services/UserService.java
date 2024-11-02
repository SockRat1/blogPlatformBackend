package com.sockrat.blogplatform.Services;

import com.sockrat.blogplatform.Models.User;
import com.sockrat.blogplatform.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public void register(User user) {
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
