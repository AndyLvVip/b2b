package uca.security.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uca.security.auth.domain.User;
import uca.security.auth.mq.UserStream;
import uca.security.auth.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@EnableBinding(UserStream.class)
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(User user) {
        User result = new User();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setName(user.getName());
        result.setPhone(user.getPhone());
        result.setEmail(user.getEmail());
        result.setPassword(passwordEncoder.encode(user.getPassword()));
        result.setCreatedOn(LocalDateTime.now());
        userRepository.save(result);
        if("exception".equals(result.getUsername())) {
            throw new RuntimeException("Seata Global Transaction Rollback.");
        }
    }

    @StreamListener(UserStream.INPUT_CHANGE_PHONE)
    public void changePhone(User user) {
        log.info("change phone for user: " + user);
        Optional<User> opResult = userRepository.findById(user.getId());
        if(opResult.isPresent()) {
            User result = opResult.get();
            result.setPhone(user.getPhone());
            userRepository.save(result);
        }
    }

    @StreamListener(UserStream.INPUT_CHANGE_EMAIL)
    public void changeEmail(User user) {
        log.info("change email for user: " + user);
        Optional<User> opResult = userRepository.findById(user.getId());
        if(opResult.isPresent()) {
            User result = opResult.get();
            result.setEmail(user.getEmail());
            userRepository.save(result);
        }
    }
}
