package uca.corporate.fs.repository;

import jooq.generated.corporate.tables.records.UserRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;
import uca.base.repository.StdStrRepository;
import uca.corporate.domain.User;

import static jooq.generated.corporate.Tables.USER;

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
