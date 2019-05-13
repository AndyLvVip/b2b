package uca.security.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import uca.security.auth.Config;
import uca.security.auth.domain.User;
import uca.security.auth.repository.UserRepository;

import java.util.Arrays;

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
            User user = userRepository.findByUsernameOrPhoneOrEmail(username, username, username);
            return user;
        };
    }

    @Bean
    @Primary
    public ClientDetailsService clientDetailsService(Config config, PasswordEncoder passwordEncoder) {
        return clientId -> {
            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(clientId);
            clientDetails.setClientSecret(passwordEncoder.encode(config.getClientSecret()));
            clientDetails.setAuthorizedGrantTypes(Arrays.asList(config.getGrantTypes()));
            clientDetails.setScope(Arrays.asList(config.getScopes()));
            return clientDetails;
        };
    }

}
