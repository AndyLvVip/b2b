package uca.ops.sys.bs.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uca.base.constant.Constants;
import uca.ops.sys.bs.repository.UserRoleRepository;
import uca.ops.sys.domain.UserRole;

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

    @Transactional
    public void linkUserRole(String userId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleRepository.insert(userRole, Constants.SYSTEM);
    }
}
