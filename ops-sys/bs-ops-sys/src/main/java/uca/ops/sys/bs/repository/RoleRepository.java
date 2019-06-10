package uca.ops.sys.bs.repository;

import jooq.generated.ops.sys.tables.records.RoleRecord;
import org.apache.commons.lang.StringUtils;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uca.base.jooq.JooqPageUtils;
import uca.base.repository.StdStrRepository;
import uca.ops.sys.domain.Role;
import uca.ops.sys.vo.UserRoleReqVo;

import static jooq.generated.ops.sys.Tables.ROLE;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/5 16:38
 */
@Repository
public class RoleRepository extends StdStrRepository<RoleRecord, Role> {

    private final DSLContext dsl;

    public RoleRepository(Configuration configuration, DSLContext dsl) {
        super(ROLE, Role.class, configuration);
        this.dsl = dsl;
    }

    public Page<Role> search(UserRoleReqVo vo, Pageable pageable) {
        Condition cond = DSL.noCondition();
        if(StringUtils.isNotEmpty(vo.getRoleName())) {
            cond = cond.and(ROLE.NAME.contains(vo.getRoleName()));
        }
        final Condition condition = cond;
        return JooqPageUtils.page(
                Role.class
                , () -> dsl.selectFrom(ROLE).where(condition).orderBy(ROLE.UPDATED_ON.desc())
                , () -> dsl.selectCount().from(ROLE).where(condition)
                , pageable
        );
    }

}
