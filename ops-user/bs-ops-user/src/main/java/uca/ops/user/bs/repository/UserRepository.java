package uca.ops.user.bs.repository;

import jooq.generated.ops.user.tables.records.UserRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.ops.user.domain.User;

import java.util.List;

import static jooq.generated.ops.user.Tables.USER;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/16 15:06
 */
@Repository
public class UserRepository extends StdStrRepository<UserRecord, User> {

    private final DSLContext dsl;
    protected UserRepository(Configuration configuration, DSLContext dsl) {
        super(USER, User.class, configuration);
        this.dsl = dsl;
    }


    public List<User> fetchList(List<String> userIds) {
        return dsl.selectFrom(USER).where(USER.ID.in(userIds)).fetchInto(User.class);
    }
}
