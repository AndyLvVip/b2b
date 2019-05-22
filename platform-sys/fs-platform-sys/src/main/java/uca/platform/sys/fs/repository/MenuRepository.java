package uca.platform.sys.fs.repository;

import jooq.generated.platform.sys.tables.records.MenuRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdLongRepository;
import uca.platform.sys.domain.Menu;

import static jooq.generated.platform.sys.Tables.MENU;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 9:10
 */
@Repository
public class MenuRepository extends StdLongRepository<MenuRecord, Menu> {

    public MenuRepository(Configuration configuration) {
        super(MENU, Menu.class, configuration);
    }
}
