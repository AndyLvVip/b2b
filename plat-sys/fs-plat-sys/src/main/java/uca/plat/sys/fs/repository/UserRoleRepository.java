package uca.plat.sys.fs.repository;

import jooq.generated.platform.sys.tables.records.UserRoleRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.ops.sys.domain.UserRole;

import static jooq.generated.platform.sys.Tables.USER_ROLE;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/14 20:13
 */
@Repository
public class UserRoleRepository extends StdStrRepository<UserRoleRecord, UserRole> {

    public UserRoleRepository(Configuration configuration) {
        super(USER_ROLE, UserRole.class, configuration);
    }

}
