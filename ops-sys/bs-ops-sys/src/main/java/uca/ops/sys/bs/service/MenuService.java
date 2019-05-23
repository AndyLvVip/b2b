package uca.ops.sys.bs.service;

import org.springframework.stereotype.Service;
import uca.ops.sys.bs.repository.MenuRepository;
import uca.ops.sys.domain.Menu;
import uca.ops.sys.domain.Permission;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 9:08
 */
@Service
public class MenuService {

    private final PermissionService permissionService;

    private final MenuRepository menuRepository;

    public MenuService(PermissionService permissionService, MenuRepository menuRepository) {
        this.permissionService = permissionService;
        this.menuRepository = menuRepository;
    }

    public List<Menu> fetchOwnMenus(String userId) {
        List<Permission> permissions = this.permissionService.fetchOwnPermissions(userId);

        List<Menu> allMenus = menuRepository.findAll();
        return Menu.filterAndAssembleMenus(allMenus, permissions);
    }
}
