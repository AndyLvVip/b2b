package uca.platform.sys.fs.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uca.base.user.StdSimpleUser;
import uca.platform.sys.domain.Menu;
import uca.platform.sys.fs.service.MenuService;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 11:49
 */
@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu/own")
    public List<Menu> fetchMenus(@AuthenticationPrincipal StdSimpleUser stdSimpleUser) {
        return menuService.fetchOwnMenus(stdSimpleUser.getId());
    }
}
