package uca.platform.sys.domain;

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

    @Column(name = "corporate_id")
    private String corporateId;

    @Column(name = "name")
    private String name;

    public enum Template {
        /**
         * 已注册
         */
        REGISTER("register"),

        ;

        public final String val;

        Template(String value) {
            this.val = value;
        }
    }
}
