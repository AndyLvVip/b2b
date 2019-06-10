package uca.ops.sys.bs.repository;

import jooq.generated.ops.sys.tables.records.UserRoleRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.ops.sys.domain.UserRole;

import java.util.List;

import static jooq.generated.ops.sys.Tables.USER_ROLE;

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

    public List<UserRole> fetchList(List<String> roleIds) {
        return dsl.selectFrom(USER_ROLE).where(USER_ROLE.ROLE_ID.in(roleIds)).fetchInto(UserRole.class);
    }
}
