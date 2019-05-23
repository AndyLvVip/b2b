package uca.ops.sys.bs.repository;

import jooq.generated.ops.sys.tables.records.MenuRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdLongRepository;
import uca.ops.sys.domain.Menu;

import static jooq.generated.ops.sys.Tables.MENU;

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
