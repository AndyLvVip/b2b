package uca.ops.sys.bs.repository;

import jooq.generated.ops.sys.tables.records.PermissionRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.ops.sys.domain.Permission;

import java.util.List;

import static jooq.generated.ops.sys.Tables.PERMISSION;
import static jooq.generated.ops.sys.Tables.USER_ROLE;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/22 11:58
 */
@Repository
public class PermissionRepository extends StdStrRepository<PermissionRecord, Permission> {

    private final DSLContext dsl;

    public PermissionRepository(Configuration configuration, DSLContext dsl) {
        super(PERMISSION, Permission.class, configuration);
        this.dsl = dsl;
    }

    public List<Permission> fetchPermissions(String userId) {
        return dsl.select(PERMISSION.fields()).from(PERMISSION)
                .join(USER_ROLE).on(USER_ROLE.ROLE_ID.eq(PERMISSION.ROLE_ID))
                .where(USER_ROLE.USER_ID.eq(userId))
                .fetchInto(Permission.class)
                ;
    }

    public List<Permission> fetchRolePermissions(String roleId) {
        return dsl.selectFrom(PERMISSION)
                .where(PERMISSION.ROLE_ID.eq(roleId))
                .fetchInto(Permission.class);
    }
}
