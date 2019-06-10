package uca.ops.sys.vo;

import lombok.Data;
import uca.ops.sys.domain.Role;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 16:29
 */
@Data
public class UserRoleRespVo {

    private Role role;

    private List<UserVo> users;
}
