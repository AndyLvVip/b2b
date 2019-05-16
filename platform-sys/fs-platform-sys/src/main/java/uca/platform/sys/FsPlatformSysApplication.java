package uca.platform.sys;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import uca.base.user.StdSimpleUser;
import uca.platform.factory.StdObjectFactory;
import uca.platform.json.StdObjectMapper;

/**
 * Created by Andy Lv on 2019/5/4
 */
@SpringBootApplication
@RefreshScope
@EnableEurekaClient
@EnableCircuitBreaker
@EnableResourceServer
public class FsPlatformSysApplication {

    @Bean
    @LoadBalanced
    public OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails details,
                                     OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

    @Bean
    ObjectMapper objectMapper() {
        return StdObjectFactory.objectMapper();
    }

    @Bean
    StdObjectMapper stdObjectMapper(ObjectMapper objectMapper) {
        return new StdObjectMapper(objectMapper);
    }

    @Bean
    PrincipalExtractor principalExtractor(ObjectMapper objectMapper) {
        return map -> objectMapper.convertValue(map, StdSimpleUser.class);
    }

    @Bean
    public UserInfoTokenServices userInfoTokenServices(ResourceServerProperties sso
            , UserInfoRestTemplateFactory restTemplateFactory
                                                       , PrincipalExtractor principalExtractor
    ) {
        UserInfoTokenServices services = new UserInfoTokenServices(
                sso.getUserInfoUri(), sso.getClientId());
        services.setRestTemplate(restTemplateFactory.getUserInfoRestTemplate());
        services.setTokenType(sso.getTokenType());
        services.setPrincipalExtractor(principalExtractor);
        return services;
    }

    public static void main(String[] args) {
        SpringApplication.run(FsPlatformSysApplication.class, args);
    }

}
