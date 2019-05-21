package uca.base.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 14:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StdPermission {

    private int menuId;

    private int permission;
}
