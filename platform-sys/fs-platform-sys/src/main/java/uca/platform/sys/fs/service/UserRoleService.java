package uca.platform.sys.fs.service;

import org.springframework.stereotype.Service;
import uca.base.constant.Constants;
import uca.platform.sys.domain.Permission;
import uca.platform.sys.domain.UserRole;
import uca.platform.sys.fs.repository.UserRoleRepository;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/15 9:00
 */
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void linkUserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.insert(userRole, Constants.SYSTEM);
    }

    public List<Permission> fetchAllPermissionList(String userId) {
        return userRoleRepository.fetchAllPermissionList(userId);
    }
}
