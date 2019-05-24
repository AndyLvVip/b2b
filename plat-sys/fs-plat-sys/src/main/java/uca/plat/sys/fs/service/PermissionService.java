package uca.plat.sys.fs.service;

import org.springframework.stereotype.Service;
import uca.ops.sys.domain.Permission;
import uca.plat.sys.fs.repository.PermissionRepository;

import java.util.List;

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
}
