package uca.security.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import uca.security.auth.Config;

@Configuration
public class Oauth2AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final TokenStore tokenStore;

    private final Config config;

    private final ClientDetailsService clientDetailsService;

    public Oauth2AuthorizationServerConfigurer(AuthenticationManager authenticationManagerBean,
                                               UserDetailsService userDetailsServiceBean,
                                               PasswordEncoder passwordEncoder,
                                               TokenStore tokenStore,
                                               ClientDetailsService clientDetailsService,
                                               Config config
    ) {
        this.authenticationManager = authenticationManagerBean;
        this.userDetailsService = userDetailsServiceBean;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.clientDetailsService = clientDetailsService;
        this.config = config;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .setClientDetailsService(clientDetailsService)
        ;
    }
}
