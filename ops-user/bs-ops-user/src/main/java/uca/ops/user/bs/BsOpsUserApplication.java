package uca.ops.user.bs;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import io.seata.rm.datasource.DataSourceProxy;
import org.jooq.conf.Settings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import uca.base.jooq.JooqUtils;
import uca.ops.user.bs.security.StdPrincipalExtractor;
import uca.ops.user.bs.service.UserService;
import uca.platform.factory.StdObjectFactory;
import uca.platform.json.StdObjectMapper;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/23 16:28
 */
@SpringBootApplication
@RefreshScope
@EnableEurekaClient
@EnableCircuitBreaker
@EnableResourceServer
@EnableFeignClients
public class BsOpsUserApplication {

    @Bean
    @LoadBalanced
    public OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails details,
                                           OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

    @Bean
    public OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
                                                                       OAuth2ProtectedResourceDetails details) {
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, details) {
            @Override
            public void apply(RequestTemplate template) {
                OAuth2AccessToken accessToken = oAuth2ClientContext.getAccessToken();
                if(null != accessToken) {
                    super.apply(template);
                }
            }
        };
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
    PrincipalExtractor principalExtractor(ObjectMapper objectMapper, UserService userService) {
        return new StdPrincipalExtractor(objectMapper, userService);
    }

    @Bean
    public UserInfoTokenServices userInfoTokenServices(ResourceServerProperties sso
            , OAuth2RestTemplate restTemplate
            , PrincipalExtractor principalExtractor
    ) {
        UserInfoTokenServices services = new UserInfoTokenServices(
                sso.getUserInfoUri(), sso.getClientId());
        services.setRestTemplate(restTemplate);
        services.setTokenType(sso.getTokenType());
        services.setPrincipalExtractor(principalExtractor);
        return services;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public Settings settings() {
        return JooqUtils.settings();
    }

    public static void main(String[] args) {
        SpringApplication.run(BsOpsUserApplication.class, args);
    }
}
