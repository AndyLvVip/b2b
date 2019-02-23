package uca.security.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uca.security.auth.domain.User;
import uca.security.auth.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void create(User user) {
        User result = new User();
        result.setUsername(user.getUsername());
        result.setPassword(passwordEncoder.encode(user.getPassword()));
        result.setCreatedOn(LocalDateTime.now());
        userRepository.save(result);
    }
}
