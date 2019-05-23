package uca.ops.sys.bs.service;

import org.springframework.stereotype.Service;
import uca.ops.sys.bs.repository.PermissionRepository;
import uca.ops.sys.domain.Permission;

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
