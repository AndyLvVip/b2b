package uca.ops.sys.bs.service;

import org.springframework.stereotype.Service;
import uca.base.user.StdPermission;
import uca.base.user.StdSimpleUser;
import uca.ops.sys.bs.repository.PermissionRepository;
import uca.ops.sys.domain.Permission;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 11:56
 */
@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> fetchOwnPermissions(String userId) {
        return this.permissionRepository.fetchPermissions(userId);
    }

    public List<StdPermission> fetchOwnStdPermission(String userId) {
        return fetchOwnPermissions(userId).stream().map(Permission::asStdPermission).collect(Collectors.toList());
    }

    public List<Permission> fetchRolePermissions(String roleId) {
        return this.permissionRepository.fetchRolePermissions(roleId);
    }

    public void savePermissions(String roleId, List<Permission> newPermissions, StdSimpleUser user) {
        List<Permission> existingPermissions = fetchRolePermissions(roleId);

        newPermissions.forEach(p -> {
            Optional<Permission> optEp = existingPermissions.stream().filter(ep -> Objects.equals(ep.getMenuId(), p.getMenuId())).findAny();
            if(optEp.isPresent()) {
                Permission ep = optEp.get();
                ep.edit(p);
                permissionRepository.update(p, user.getUsername());
            }else {
                Permission np = new Permission();
                np.create(roleId, p);
                permissionRepository.insert(p, user.getUsername());
            }
        });

        permissionRepository.delete(Permission.fetchToBeDeletedList(existingPermissions, newPermissions));
    }
}
