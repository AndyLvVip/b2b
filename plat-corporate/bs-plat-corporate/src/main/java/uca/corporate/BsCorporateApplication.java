package uca.corporate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by Andy Lv on 2019/5/4
 */
@SpringBootApplication
@RefreshScope
@EnableEurekaClient
@EnableCircuitBreaker
@EnableResourceServer
public class BsCorporateApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsCorporateApplication.class, args);
    }
}
