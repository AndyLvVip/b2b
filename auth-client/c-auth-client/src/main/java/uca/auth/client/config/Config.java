package uca.auth.client.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "uca.oauth2")
@Configuration
@Data
public class Config {

    private Client client;

    @Data
    public static class Client {

        private String id;

        private String secret;

        private String tokenUrl;

        private Scope web;

        private Scope mobile;

        @Data
        public static class Scope {

            private String name;

            private Integer timeout;

        }

    }
}
