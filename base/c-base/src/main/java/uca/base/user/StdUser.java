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

    private String id;

    private String username;

    private String name;

    private String phone;

    private String email;

    private StdCorporate stdCorporate;

    private List<StdPermission> stdPermissionList;

}
