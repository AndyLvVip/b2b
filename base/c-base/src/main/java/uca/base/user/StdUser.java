package uca.base.user;

import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 14:44
 */
@Data
public class StdUser {

    private StdSimpleUser stdSimpleUser;

    private StdCorporate stdCorporate;

    private List<StdPermission> stdPermissionList;

}
