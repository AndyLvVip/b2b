package uca.security.auth;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import uca.security.auth.domain.User;
import uca.security.auth.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    private final ConsumerTokenServices consumerTokenServices;

    public UserController(UserService userService
            , ConsumerTokenServices consumerTokenServices) {
        this.userService = userService;
        this.consumerTokenServices = consumerTokenServices;
    }

    @GetMapping("/user")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> result = new HashMap<>();
        if(user.getUserAuthentication().getPrincipal() instanceof User)
            ((User) user.getUserAuthentication().getPrincipal()).setPassword(null);//clear the password before returning
        result.put("user", user.getUserAuthentication().getPrincipal());
        return result;
    }

    @PostMapping("/user/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody User user) {
        userService.create(user);
    }


    @PostMapping("/oauth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String bearer) {
        if(StringUtils.isNotEmpty(bearer)) {
            String access_token = bearer.replace("Bearer ", "");
            consumerTokenServices.revokeToken(access_token);
        }
    }

}
