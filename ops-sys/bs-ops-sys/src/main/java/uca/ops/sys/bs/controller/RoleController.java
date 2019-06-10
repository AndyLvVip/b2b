package uca.ops.sys.bs.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uca.base.user.StdSimpleUser;
import uca.ops.sys.bs.service.RoleService;
import uca.ops.sys.domain.Role;
import uca.ops.sys.vo.RoleMenuVo;
import uca.ops.sys.vo.UserRoleReqVo;
import uca.ops.sys.vo.UserRoleRespVo;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 18:14
 */
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/role")
    @PreAuthorize("@accessControl.roleMenu.canView(@permissionService.fetchOwnStdPermission(#user.getId()))")
    public Page<UserRoleRespVo> search(@AuthenticationPrincipal StdSimpleUser user, UserRoleReqVo vo, Pageable pageable) {
        return roleService.search(vo, pageable);
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@accessControl.roleMenu.canCreate(@permissionService.fetchOwnStdPermission(#user.getId()))")
    public void create(@RequestBody Role role, @AuthenticationPrincipal StdSimpleUser user) {
        roleService.create(role, user);
    }

    @PutMapping("/role/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@accessControl.roleMenu.canEdit(@permissionService.fetchOwnStdPermission(#user.getId()))")
    public void edit(@PathVariable("id") String id, @RequestBody Role role, @AuthenticationPrincipal StdSimpleUser user) {
        role.setId(id);
        roleService.edit(role, user);
    }

    @DeleteMapping("/role/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@accessControl.roleMenu.canDelete(@permissionService.fetchOwnStdPermission(#user.getId()))")
    public void delete(@PathVariable("id") String id, @RequestBody Role role, @AuthenticationPrincipal StdSimpleUser user) {
        role.setId(id);
        roleService.delete(role);
    }

    @GetMapping("/role/{id}/permission")
    @PreAuthorize("@accessControl.roleMenu.canViewPermission(@permissionService.fetchOwnStdPermission(#user.getId()))")
    public RoleMenuVo fetchPermission4Role(@PathVariable("id") String id, @AuthenticationPrincipal StdSimpleUser user) {
        return roleService.fetchPermission4Role(id);
    }

    @PutMapping("/role/{id}/permission")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@accessControl.roleMenu.canEditPermission(@permissionService.fetchOwnStdPermission(#user.getId()))")
    public void editPermission4Role(@PathVariable("id") String id, @RequestBody RoleMenuVo vo, @AuthenticationPrincipal StdSimpleUser user) {
        vo.validateRole();
        vo.getRole().setId(id);
        roleService.editPermission4Role(vo, user);
    }

}
