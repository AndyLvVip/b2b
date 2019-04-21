package uca.platform.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import wcsc.common.domain.StdStrDomain;

import javax.persistence.Column;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Permission extends StdStrDomain {

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "permission")
    private Long permission;

}
