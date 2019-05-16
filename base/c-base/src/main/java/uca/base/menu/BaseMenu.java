package uca.base.menu;

import uca.base.menu.vo.PermissionUnitVo;
import uca.base.menu.vo.PermissionVo;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 10:02
 */
public abstract class BaseMenu {

    public abstract int menuId();

    protected boolean hasPermission(List<PermissionVo> permissionVoList, PermissionUnitVo permissionUnitVo) {
        return permissionVoList.stream().filter(p -> p.getMenuId() == menuId())
                .anyMatch(p -> (p.getPermission() & permissionUnitVo.getPermissionUnit()) == permissionUnitVo.getPermissionUnit())
                ;
    }

    public boolean canView(List<PermissionVo> permissionVoList) {
        return hasPermission(permissionVoList, PermissionUnitVo.VIEW);
    }

    public boolean canCreate(List<PermissionVo> permissionVoList) {
        return hasPermission(permissionVoList, PermissionUnitVo.CREATE);
    }

    public boolean canEdit(List<PermissionVo> permissionVoList) {
        return hasPermission(permissionVoList, PermissionUnitVo.EDIT);
    }

    public boolean canDelete(List<PermissionVo> permissionVoList) {
        return hasPermission(permissionVoList, PermissionUnitVo.DELETE);
    }

}
