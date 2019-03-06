package uca.auth.client;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import uca.auth.client.config.AppConfiguration;
import uca.auth.client.service.SecurityService;
import uca.auth.client.vo.OAuth2TokenVo;

import java.util.Base64;

/**
 * Created by andy.lv
 * on: 2019/1/21 13:49
 */
@RestController
public class AuthClientController {

    private SecurityService securityService;

    public AuthClientController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public OAuth2TokenVo login(@RequestHeader("Authorization") String authorization) {
        String credential = new String(Base64.getDecoder().decode(authorization.replace("Basic ", "")));
        String username = credential.substring(0, credential.indexOf(":"));
        String password = credential.substring(credential.indexOf(":") + 1);

        return securityService.login(username, password);
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
