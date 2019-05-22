package uca.platform.sys.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdLongDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "menu")
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

    private List<Menu> subMenus;

    private static List<Menu> assembleSubMenus(List<Menu> allMenus) {
        List<Menu> level1Menu = allMenus.stream().filter(m -> null == m.getParentId()).collect(Collectors.toList());

        level1Menu.forEach(m ->
            m.setSubMenus(allMenus.stream().filter(a -> Objects.equals(m.getId(), a.getParentId())).collect(Collectors.toList()))
        );
        return level1Menu;
    }

    public static List<Menu> filterAndAssembleMenus(List<Menu> allMenus, List<Permission> permissions) {
        List<Menu> assembledMenus = assembleSubMenus(allMenus);
        return assembledMenus.stream().map(m -> {
            m.setSubMenus(
                    m.getSubMenus().stream().filter(sm ->
                            permissions.stream().anyMatch(p -> Objects.equals(p.getMenuId(), sm.getId()))
                    ).collect(Collectors.toList())
            );
            return m;
        }).filter(m -> !m.getSubMenus().isEmpty())
                .collect(Collectors.toList());
    }

}
