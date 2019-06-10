package uca.ops.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "role")
@Entity
public class Role extends StdStrDomain {

    @Column(name = "name")
    private String name;

    @Column(name = "remark")
    private String remark;

    public void create(Role role) {
        setName(role.getName());
        setRemark(role.getRemark());
    }

    public void edit(Role role) {
        setName(role.getName());
        setRemark(role.getRemark());
        setVersion(role.getVersion());
    }

    public void delete(Integer version) {
        setVersion(version);
    }

}
