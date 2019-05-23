package uca.ops.sys.bs.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uca.base.user.StdSimpleUser;
import uca.ops.sys.bs.service.PermissionService;
import uca.ops.sys.domain.Permission;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 11:55
 */
@RestController
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permission/own")
    public List<Permission> fetchAllPermissionList(@AuthenticationPrincipal StdSimpleUser stdSimpleUser) {
        return this.permissionService.fetchOwnPermissions(stdSimpleUser.getId());
    }
}
