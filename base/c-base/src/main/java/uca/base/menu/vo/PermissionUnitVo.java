package uca.base.menu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uca.platform.BitUtils;

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

    public static final PermissionUnitVo VIEW = new PermissionUnitVo("查看", BitUtils.bitValue(0));

    public static final PermissionUnitVo CREATE = new PermissionUnitVo("创建", BitUtils.bitValue(1));

    public static final PermissionUnitVo EDIT = new PermissionUnitVo("编辑", BitUtils.bitValue(2));

    public static final PermissionUnitVo DELETE = new PermissionUnitVo("删除", BitUtils.bitValue(3));

    private String label;

    private int permissionUnit;

}
