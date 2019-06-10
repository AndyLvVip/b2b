package uca.ops.sys.bs.repository;

import jooq.generated.ops.sys.tables.records.PermissionUnitRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.ops.sys.domain.PermissionUnit;

import java.util.List;

import static jooq.generated.ops.sys.Tables.PERMISSION_UNIT;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/6 14:02
 */
@Repository
public class PermissionUnitRepository extends StdStrRepository<PermissionUnitRecord, PermissionUnit> {

    private DSLContext dsl;

    public PermissionUnitRepository(Configuration configuration, DSLContext dsl) {
        super(PERMISSION_UNIT, PermissionUnit.class, configuration);
        this.dsl = dsl;
    }

    public List<PermissionUnit> fetchAllWithSequence() {
        return dsl.selectFrom(PERMISSION_UNIT)
                .orderBy(PERMISSION_UNIT.LABEL.asc())
                .fetchInto(PermissionUnit.class);
    }
}
