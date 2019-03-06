package uca.auth.client.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfiguration {

    @ConfigurationProperties(prefix = "uca.oauth2.client")
    @Data
    @Configuration
    public static class AuthClient {

        private String id;

        private String secret;

        private String scope;
    }
}
