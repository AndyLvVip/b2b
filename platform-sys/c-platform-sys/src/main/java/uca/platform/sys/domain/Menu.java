package uca.platform.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import wcsc.common.domain.StdLongDomain;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Menu extends StdLongDomain {

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sequence")
    private BigDecimal sequence;

    @Column(name = "icon")
    private String icon;

}
