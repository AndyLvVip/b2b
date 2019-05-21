package uca.corporate.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;
import uca.base.fs.constant.CorporateType;
import uca.base.fs.constant.RoleTemplate;
import uca.base.user.StdCorporate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Andy Lv on 2019/5/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "corporate")
public class Corporate extends StdStrDomain {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "logo_file_id")
    private String logoFileId;

    @Column(name = "fax_num")
    private String faxNum;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address_id")
    private String addressId;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_role_id")
    private String lastRoleId;

    public static Corporate newInstance(String name) {
        Corporate corporate = new Corporate();
        corporate.setName(name);
        corporate.setType(CorporateType.UNKNOWN.val);
        corporate.setVerified(false);
        corporate.setActive(true);
        corporate.setLastRoleId(RoleTemplate.REGISTER.val);
        return corporate;
    }

    public StdCorporate toStdCorporate() {
        StdCorporate stdCorporate = new StdCorporate();
        stdCorporate.setId(getId());
        stdCorporate.setName(getName());
        stdCorporate.setType(getType());
        return stdCorporate;
    }
}
