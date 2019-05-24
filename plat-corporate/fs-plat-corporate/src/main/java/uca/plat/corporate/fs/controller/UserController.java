package uca.plat.corporate.fs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uca.base.fs.user.StdUser;
import uca.base.user.StdSimpleUser;
import uca.corporate.domain.User;
import uca.plat.corporate.fs.service.UserService;

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

    @GetMapping("/user/detail")
    @PreAuthorize("@accessControlMenu.BASE_INFO_MENU.canView(#stdUser.stdPermissionList)")
    public User info(@AuthenticationPrincipal StdUser stdUser) {
        return userService.fetchUserDetailWithCorporate(stdUser.getStdSimpleUser().getId());
    }

}
