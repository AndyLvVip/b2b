package uca.ops.user.bs.repository;

import jooq.generated.ops.user.tables.records.UserRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.ops.user.domain.User;

import static jooq.generated.ops.user.Tables.USER;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 15:06
 */
@Repository
public class UserRepository extends StdStrRepository<UserRecord, User> {

    protected UserRepository(Configuration configuration) {
        super(USER, User.class, configuration);
    }
}
