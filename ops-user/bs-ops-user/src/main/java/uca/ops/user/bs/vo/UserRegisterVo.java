package uca.ops.user.bs.vo;

import lombok.Data;
import uca.base.user.StdSimpleUser;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/23 18:00
 */
@Data
public class UserRegisterVo {

    private StdSimpleUser stdSimpleUser;

    private List<String> roleIds;
}
