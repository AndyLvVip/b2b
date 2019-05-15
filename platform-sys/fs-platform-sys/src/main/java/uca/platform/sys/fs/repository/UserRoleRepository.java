package uca.platform.sys.fs.repository;

import jooq.generated.platform.sys.tables.records.UserRoleRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.platform.sys.domain.Permission;
import uca.platform.sys.domain.UserRole;

import java.util.List;

import static jooq.generated.platform.sys.Tables.PERMISSION;
import static jooq.generated.platform.sys.Tables.USER_ROLE;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/14 20:13
 */
@Repository
public class UserRoleRepository extends StdStrRepository<UserRoleRecord, UserRole> {

    private final DSLContext dsl;

    public UserRoleRepository(Configuration configuration, DSLContext dsl) {
        super(USER_ROLE, UserRole.class, configuration);
        this.dsl = dsl;
    }


    public List<Permission> fetchAllPermissionList(String userId) {
        return dsl.select(PERMISSION.fields())
                .from(USER_ROLE).join(PERMISSION).on(USER_ROLE.ROLE_ID.eq(PERMISSION.ROLE_ID))
                .where(USER_ROLE.USER_ID.eq(userId))
                .fetchInto(Permission.class);
    }
}
