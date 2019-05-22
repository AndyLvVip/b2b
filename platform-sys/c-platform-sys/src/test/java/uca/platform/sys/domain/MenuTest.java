package uca.platform.sys.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 12:44
 */
public class MenuTest {

    @Test
    public void assembleMenuList() {
        List<Menu> allMenus = new ArrayList<>();
        Menu p1 = new Menu();
        p1.setId(1L);
        p1.setParentId(null);

        Menu s11 = new Menu();
        s11.setId(2L);
        s11.setParentId(1L);

        Menu s12 = new Menu();
        s12.setId(3L);
        s12.setParentId(1L);

        Menu p2 = new Menu();
        p2.setId(4L);
        p2.setParentId(null);

        Menu s21 = new Menu();
        s21.setId(5L);
        s21.setParentId(4L);

        allMenus.add(p1);
        allMenus.add(p2);
        allMenus.add(s11);
        allMenus.add(s12);
        allMenus.add(s21);

        List<Permission> ps = new ArrayList<>();

        Permission pm = new Permission();
        pm.setMenuId(2L);
        pm.setPermission(1L);


        ps.add(pm);

        List<Menu> assembleMenus = Menu.filterAndAssembleMenus(allMenus, Arrays.asList(pm));
        assertEquals(1, assembleMenus.size());
        assertEquals(1, assembleMenus.get(0).getSubMenus().size());
        assertEquals(1L, assembleMenus.get(0).getId().longValue());
        assertEquals(2L, assembleMenus.get(0).getSubMenus().get(0).getId().longValue());
    }
}
