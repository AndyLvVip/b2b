package uca.ops.sys.bs.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uca.base.user.StdSimpleUser;
import uca.ops.sys.bs.feign.OpsUserFeignClient;
import uca.ops.sys.bs.repository.MenuRepository;
import uca.ops.sys.bs.repository.PermissionRepository;
import uca.ops.sys.bs.repository.PermissionUnitRepository;
import uca.ops.sys.bs.repository.RoleRepository;
import uca.ops.sys.domain.*;
import uca.ops.sys.vo.RoleMenuVo;
import uca.ops.sys.vo.UserRoleReqVo;
import uca.ops.sys.vo.UserRoleRespVo;
import uca.ops.sys.vo.UserVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 17:37
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final UserRoleService userRoleService;

    private final OpsUserFeignClient opsUserFeignClient;

    private final MenuRepository menuRepository;

    private final PermissionRepository permissionRepository;

    private final PermissionUnitRepository permissionUnitRepository;

    private final PermissionService permissionService;

    public RoleService(RoleRepository roleRepository,
                       UserRoleService userRoleService,
                       OpsUserFeignClient opsUserFeignClient,
                       MenuRepository menuRepository,
                       PermissionRepository permissionRepository,
                       PermissionUnitRepository permissionUnitRepository,
                       PermissionService permissionService
    ) {
        this.permissionUnitRepository = permissionUnitRepository;
        this.roleRepository = roleRepository;
        this.userRoleService = userRoleService;
        this.opsUserFeignClient = opsUserFeignClient;
        this.menuRepository = menuRepository;
        this.permissionRepository = permissionRepository;
        this.permissionService = permissionService;
    }

    public Page<UserRoleRespVo> search(UserRoleReqVo vo, Pageable pageable) {
        Page<Role> roles = roleRepository.search(vo, pageable);
        List<UserRole> userRoles = userRoleService.fetchUserRoles(roles.stream().map(Role::getId).collect(Collectors.toList()));
        List<String> userIds = userRoles.stream().map(UserRole::getUserId).collect(Collectors.toList());

        List<UserVo> list = Collections.emptyList();
        if(CollectionUtils.isNotEmpty(userIds)) {
            list = opsUserFeignClient.fetchList(userIds);
        }
        final List<UserVo> users = list;

        List<UserRoleRespVo> results = new ArrayList<>();

        roles.forEach(r -> {
            UserRoleRespVo result = new UserRoleRespVo();
            result.setRole(r);
            List<String> uIds = userRoles.stream().filter(ur -> Objects.equals(ur.getRoleId(), r.getId())).map(UserRole::getUserId).collect(Collectors.toList());
            result.setUsers(users.stream().filter(u -> uIds.contains(u.getId())).collect(Collectors.toList()));
            results.add(result);
        });
        return new PageImpl<>(results, pageable, roles.getTotalElements());
    }

    public void edit(Role role, StdSimpleUser user) {
        Role result = roleRepository.forceFindById(role.getId());
        result.edit(role);

        roleRepository.update(role, user.getUsername());
    }

    public void delete(Role role) {
        Role result = roleRepository.forceFindById(role.getId());
        result.delete(role.getVersion());

        roleRepository.delete(result);
    }

    public void create(Role role, StdSimpleUser user) {
        Role result = new Role();
        result.create(role);
        roleRepository.insert(result, user.getUsername());
    }

    public RoleMenuVo fetchPermission4Role(String roleId) {
        Role role = roleRepository.forceFindById(roleId);
        List<Permission> rolePermissions = permissionRepository.fetchRolePermissions(roleId);
        List<PermissionUnit> permissionUnits = permissionUnitRepository.fetchAllWithSequence();
        permissionUnits.forEach(pu -> pu.initGranted(rolePermissions));
        List<Menu> menus = menuRepository.findAll();
        menus.forEach(m -> m.linkPermissionUnits(permissionUnits));
        List<Menu> menuWithSubMenus = Menu.assembleSubMenus(menus);
        RoleMenuVo result = new RoleMenuVo();
        result.setRole(role);
        result.setMenus(menuWithSubMenus);
        return result;
    }

    public void editPermission4Role(RoleMenuVo vo, StdSimpleUser user) {
        Role role = roleRepository.forceFindById(vo.getRole().getId());
        roleRepository.update(role, user.getUsername());

        List<PermissionUnit> permissionUnits = permissionUnitRepository.fetchAllWithSequence();
        List<Permission> newPermissions = vo.getMenus().stream().map(m -> m.buildPermission(role.getId(), permissionUnits)).filter(Objects::nonNull)
                .collect(Collectors.toList());
        permissionService.savePermissions(role.getId(), newPermissions, user);
    }
}
