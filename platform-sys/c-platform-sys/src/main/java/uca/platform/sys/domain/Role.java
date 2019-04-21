package uca.platform.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import wcsc.common.domain.StdStrDomain;

import javax.persistence.Column;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Role extends StdStrDomain {

    @Column(name = "corporate_id")
    private String corporateId;

    @Column(name = "name")
    private String name;
}
