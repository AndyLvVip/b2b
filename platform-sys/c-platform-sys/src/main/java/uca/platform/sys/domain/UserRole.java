package uca.platform.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Andy Lv on 2019/4/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "user_role")
public class UserRole extends StdStrDomain {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private String roleId;

}
