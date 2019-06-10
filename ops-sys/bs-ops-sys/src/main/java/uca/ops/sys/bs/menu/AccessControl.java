package uca.ops.sys.bs.menu;

import org.springframework.stereotype.Component;
import uca.base.menu.BaseMenu;
import uca.base.user.StdPermission;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/10 12:08
 */
@Component
public class AccessControl {

    public final RoleMenu roleMenu = new RoleMenu();

    public static class RoleMenu extends BaseMenu {

        public static final int VIEW_PERMISSION = 1 << 4;

        public static final int EDIT_PERMISSION = 1 << 5;

        @Override
        public int menuId() {
            return 34;
        }

        public boolean canViewPermission(List<StdPermission> stdPermissionList) {
            return canDo(stdPermissionList, VIEW_PERMISSION);
        }

        public boolean canEditPermission(List<StdPermission> stdPermissionList) {
            return canDo(stdPermissionList, EDIT_PERMISSION);
        }
    }
}
