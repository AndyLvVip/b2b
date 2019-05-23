package uca.ops.sys.bs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uca.ops.sys.bs.service.UserRoleService;

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

    @PostMapping("/public/linkUserRole/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkUserRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {
        this.userRoleService.linkUserRole(userId, roleIds);
    }

}
