package uca.ops.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;
import uca.base.user.StdPermission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "permission")
public class Permission extends StdStrDomain {

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "permission")
    private Integer permission;

    public static List<Permission> fetchToBeDeletedList(List<Permission> oldPermissions, List<Permission> newPermissions) {
        return oldPermissions.stream()
                .filter(op -> !newPermissions.stream().anyMatch(np -> Objects.equals(np.getMenuId(), op.getMenuId())))
                .collect(Collectors.toList());
    }

    public void create(String roleId, Permission p) {
        setRoleId(roleId);
        setMenuId(p.getMenuId());
        setPermission(p.getPermission());
    }

    public void edit(Permission p) {
        setPermission(p.getPermission());
    }


    public StdPermission asStdPermission() {
        return new StdPermission(getMenuId(), getPermission());
    }

}
