package uca.security.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author andy
 * On @date 19-2-24 上午12:08
 */
@ConfigurationProperties(prefix = "auth-server")
@Configuration
@Data
public class Config {

    private String clientId;

    private String clientSecret;

    private String[] grantTypes;

    private String[] scopes;
}
