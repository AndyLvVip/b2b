package uca.corporate.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Andy Lv on 2019/5/15
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends StdStrDomain {

    private String username;

    private String memberOf;

    private String name;

    private String phone;

    private String email;

    private String qq;

    private Integer status;
}
