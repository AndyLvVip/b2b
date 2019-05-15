package uca.platform.sys.fs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uca.platform.sys.fs.service.UserRoleService;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/15 9:39
 */
@Controller
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/public/linkUserRole/{userId}/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkUserRole(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId) {
        this.userRoleService.linkUserRole(userId, roleId);
    }

}
