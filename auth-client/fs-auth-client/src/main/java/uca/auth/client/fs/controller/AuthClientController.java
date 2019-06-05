package uca.auth.client.fs.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import uca.auth.client.config.Config;
import uca.auth.client.exception.InvalidAccessTokenException;
import uca.auth.client.service.SecurityService;
import uca.auth.client.vo.OAuth2TokenVo;

import java.util.Base64;

/**
 * Created by andy.lv
 * on: 2019/1/21 13:49
 */
@RestController
public class AuthClientController {

    private final SecurityService securityService;

    private final Config config;

    public AuthClientController(SecurityService securityService,
                                Config config) {
        this.securityService = securityService;
        this.config = config;
    }

    @PostMapping("/web/login")
    public OAuth2TokenVo webLogin(@RequestHeader("Authorization") String authorization) {
        return login(this.config.getClient().getWeb(), authorization);
    }

    private OAuth2TokenVo login(Config.Client.Scope scope, String authorization) {
        String credential = new String(Base64.getDecoder().decode(authorization.replace("Basic ", "")));
        String username = credential.substring(0, credential.indexOf(":"));
        String password = credential.substring(credential.indexOf(":") + 1);

        return securityService.login(scope, username, password);
    }

    @PostMapping("/mobile/login")
    public OAuth2TokenVo mobileLogin(@RequestHeader("Authorization") String authorization) {
        return login(this.config.getClient().getMobile(), authorization);
    }

    @PostMapping("/web/refreshToken")
    public OAuth2TokenVo webRefreshToken(@RequestHeader("Authorization") String authorization) {
        return refreshToken(this.config.getClient().getWeb(), authorization);
    }

    @PostMapping("/mobile/refreshToken")
    public OAuth2TokenVo mobileRefreshToken(@RequestHeader("Authorization") String authorization) {
        return refreshToken(this.config.getClient().getMobile(), authorization);
    }

    private OAuth2TokenVo refreshToken(Config.Client.Scope scope, String authorization) {
        if(StringUtils.isEmpty(authorization)) {
            throw new InvalidAccessTokenException("empty authorization");
        }
        String accessToken = authorization.replace("Bearer ", "");
        return this.securityService.refreshToken(scope, accessToken);
    }

}
