package uca.corporate.fs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uca.base.user.StdSimpleUser;
import uca.base.user.StdUser;
import uca.corporate.fs.service.UserService;

import javax.validation.Valid;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/17 10:33
 */
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/basic")
    public StdUser user(@AuthenticationPrincipal StdUser stdUser) {
        return stdUser;
    }

    @PostMapping("/public/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@RequestBody @Valid StdSimpleUser user) {
        userService.register(user);
    }

}
