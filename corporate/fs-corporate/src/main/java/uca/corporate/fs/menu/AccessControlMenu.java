package uca.corporate.fs.menu;

import org.springframework.stereotype.Component;
import uca.base.menu.BaseMenu;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 8:59
 */
@Component
public class AccessControlMenu {

    public static class BaseInfoMenu extends BaseMenu {

        @Override
        public int menuId() {
            return 2;
        }

    }
}
