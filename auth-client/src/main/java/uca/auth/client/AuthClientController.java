package uca.auth.client;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uca.auth.client.config.Config;
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

    @PostMapping("/refreshToken")
    public String refreshToken(String access_token) {
        return null;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(String access_token) {

    }
}
