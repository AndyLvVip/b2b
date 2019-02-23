package uca.security.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uca.security.auth.domain.User;
import uca.security.auth.repository.UserRepository;

import java.util.Optional;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public WebSecurityConfigurer(BCryptPasswordEncoder passwordEncoder,
                                 UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        return username -> {
            Optional<User> user = userRepository.findById(username);
            if (user.isPresent())
                return user.get();
            return null;
        };
    }

}
