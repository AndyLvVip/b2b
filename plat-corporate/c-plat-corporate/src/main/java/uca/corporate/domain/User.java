package uca.corporate.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;
import uca.base.user.StdSimpleUser;

import javax.persistence.Column;
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

    @Column(name = "username")
    private String username;

    @Column(name = "member_of")
    private String memberOf;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "qq")
    private String qq;

    @Column(name = "enabled")
    private Boolean enabled;

    private Corporate corporate;

    public static User newInstance(StdSimpleUser simpleUser, String memberOf) {
        User user = new User();
        user.setUsername(simpleUser.getUsername());
        user.setMemberOf(memberOf);
        user.setName(simpleUser.getName());
        user.setPhone(simpleUser.getPhone());
        user.setEmail(simpleUser.getEmail());
        user.setEnabled(true);
        return user;
    }

    public StdSimpleUser toStdSimpleUser() {
        StdSimpleUser stdSimpleUser = new StdSimpleUser();
        stdSimpleUser.setId(getId());
        stdSimpleUser.setName(getName());
        stdSimpleUser.setUsername(getUsername());
        stdSimpleUser.setPhone(getPhone());
        stdSimpleUser.setEmail(getEmail());
        return stdSimpleUser;
    }
}
