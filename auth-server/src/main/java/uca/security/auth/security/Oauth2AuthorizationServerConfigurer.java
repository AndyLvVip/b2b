package uca.security.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class Oauth2AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final TokenStore tokenStore;

    public Oauth2AuthorizationServerConfigurer(AuthenticationManager authenticationManagerBean,
                                               UserDetailsService userDetailsServiceBean,
                                               PasswordEncoder passwordEncoder,
                                               TokenStore tokenStore
    ) {
        this.authenticationManager = authenticationManagerBean;
        this.userDetailsService = userDetailsServiceBean;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("portal")
                .secret(passwordEncoder.encode("portal$ecret"))
                .authorizedGrantTypes(
                        "refresh_token",
                        "password",
                        "client_credential"
                )
                .scopes("webclient", "mobileclient")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
        ;
    }
}
