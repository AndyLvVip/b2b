package uca.platform.sys.fs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uca.platform.sys.domain.Permission;
import uca.platform.sys.fs.service.UserRoleService;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/15 9:39
 */
@RestController
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


    @GetMapping("/permission/user/{userId}")
    public List<Permission> fetchAllPermissionList(@PathVariable("userId") String userId) {
        return this.userRoleService.fetchAllPermissionList(userId);
    }
}
