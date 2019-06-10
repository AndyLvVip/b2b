package uca.ops.sys.vo;

import lombok.Data;
import uca.ops.sys.domain.Menu;
import uca.ops.sys.domain.Role;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/6 14:51
 */
@Data
public class RoleMenuVo {

    private Role role;

    private List<Menu> menus;

    public RoleMenuVo validateRole() {
        if(null == role) {
            throw new IllegalArgumentException("role can not be null");
        }
        return this;
    }

}
