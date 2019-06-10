package uca.base.menu;

import uca.base.constant.Constants;
import uca.base.user.StdPermission;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 10:02
 */
public abstract class BaseMenu {

    public abstract int menuId();

    protected boolean canDo(List<StdPermission> permissionVoList, int permissionUnit) {
        return permissionVoList.stream().filter(p -> p.getMenuId() == menuId())
                .anyMatch(p -> (p.getPermission() & permissionUnit) == permissionUnit)
                ;
    }

    public boolean canView(List<StdPermission> permissionVoList) {
        return canDo(permissionVoList, Constants.PermissionUnit.VIEW);
    }

    public boolean canCreate(List<StdPermission> permissionVoList) {
        return canDo(permissionVoList, Constants.PermissionUnit.CREATE);
    }

    public boolean canEdit(List<StdPermission> permissionVoList) {
        return canDo(permissionVoList, Constants.PermissionUnit.EDIT);
    }

    public boolean canDelete(List<StdPermission> permissionVoList) {
        return canDo(permissionVoList, Constants.PermissionUnit.DELETE);
    }

}
