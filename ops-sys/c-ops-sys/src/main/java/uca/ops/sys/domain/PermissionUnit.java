package uca.ops.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/6 13:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "permission_unit")
@Entity
public class PermissionUnit extends StdStrDomain {

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "unit")
    private Integer unit;

    @Column(name = "label")
    private String label;

    private Boolean granted;

    public void initGranted(List<Permission> permissions) {
        setGranted(
                permissions.stream().filter(p -> Objects.equals(p.getMenuId(), getMenuId()))
                        .anyMatch(p -> (p.getPermission() & getUnit()) == getUnit())
        );
    }
}
