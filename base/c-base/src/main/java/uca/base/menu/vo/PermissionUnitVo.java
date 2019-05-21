package uca.base.menu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 10:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionUnitVo {

    public static final PermissionUnitVo VIEW = new PermissionUnitVo("查看", 1);

    public static final PermissionUnitVo CREATE = new PermissionUnitVo("创建", 1 << 1);

    public static final PermissionUnitVo EDIT = new PermissionUnitVo("编辑", 1 << 2);

    public static final PermissionUnitVo DELETE = new PermissionUnitVo("删除", 1 << 3);

    private String label;

    private int permissionUnit;

}
